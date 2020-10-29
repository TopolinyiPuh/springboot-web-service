package by.vstk.controller;

import by.vstk.model.Literature;
import by.vstk.model.User;
import by.vstk.service.impl.DisciplineServiceImpl;
import by.vstk.service.impl.LiteratureServiceImpl;
import by.vstk.service.impl.SpecialityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class LiteratureController {
    private final LiteratureServiceImpl service;
    private final SpecialityServiceImpl specService;
    private final DisciplineServiceImpl discipService;

    @Autowired
    public LiteratureController(LiteratureServiceImpl service, SpecialityServiceImpl specService, DisciplineServiceImpl discipService) {
        this.service = service;
        this.specService = specService;
        this.discipService = discipService;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("tehn", specService.getSpecialitiesByDep(1L));
        model.addAttribute("arh", specService.getSpecialitiesByDep(2L));
        model.addAttribute("prom", specService.getSpecialitiesByDep(4L));
        model.addAttribute("prof", specService.getSpecialitiesByDep(5L));
        model.addAttribute("vech", specService.getSpecialitiesByDep(3L));
        model.addAttribute("zaoch", specService.getSpecialitiesByDep(6L));
        return "main";
    }

    @GetMapping("/speciality")
    public String disciplinesListBySpeciality(@RequestParam Long id, String course, Model model) {
        model.addAttribute("I", discipService.getDisciplinesBySpecialityAndCourse(id, "1"));
        model.addAttribute("II", discipService.getDisciplinesBySpecialityAndCourse(id, "2"));
        model.addAttribute("III", discipService.getDisciplinesBySpecialityAndCourse(id, "3"));
        model.addAttribute("IV", discipService.getDisciplinesBySpecialityAndCourse(id, "4"));
        return "speciality";
    }

//    @GetMapping("/literature")
//    public String advancedLitList(Model model, @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
//        model.addAttribute("literature", service.getAll(pageable));
//        return "lit_list";
//    }

    @GetMapping("/literature")
    public String listLiterature(
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC, value = 50) Pageable pageable,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(50);

        Page<Literature> literaturePage = service.getAll(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("literaturePage", literaturePage);

        int totalPages = literaturePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "lit_list";
    }



    @GetMapping("/literature/add")
    public String displayAddForm(Model model) {
        model.addAttribute("literature", new Literature());
        model.addAttribute("departments", service.getDepartments());
        model.addAttribute("specialities", specService.getSpecialities());
        model.addAttribute("disciplines", discipService.getDisciplines());
        model.addAttribute("types", service.getTypes());
        return "lit_create";
    }

    @PostMapping("/literature/add")
    public String add(@ModelAttribute @Valid Literature newLiterature,
                      Errors errors, @RequestParam Long departmentId,
                      @RequestParam Long specialityId, @RequestParam Long disciplineId, @RequestParam Long typeId,
                      @RequestParam MultipartFile[] files, @AuthenticationPrincipal User user, Model model) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("departments", service.getDepartments());
            model.addAttribute("specialities", specService.getSpecialities());
            model.addAttribute("disciplines", discipService.getDisciplines());
            model.addAttribute("types", service.getTypes());
            return "forward:/add";
        }

        service.create(newLiterature, departmentId, specialityId, disciplineId, typeId, files, user);
        return "redirect:/literature/add";
    }

    @Transactional
    @GetMapping("/literature/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId){
        Literature doc = service.getFile(fileId);
        if (doc.getDocType().contains("video") || doc.getDocType().contains("image")) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(doc.getDocType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + doc.getDocName() + "\"")
                    .body(new ByteArrayResource(doc.getData()));
        } else return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + doc.getDocName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }
}
