package by.vstk.controller;

import by.vstk.model.Literature;
import by.vstk.model.LiteratureRemoved;
import by.vstk.model.User;
import by.vstk.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.stream.IntStream;

@Controller
public class LiteratureController {
    private final LiteratureServiceImpl service;
    private final SpecialityServiceImpl specService;
    private final DisciplineServiceImpl discipService;
    private final LiteratureSearch searchService;
    private final LiteratureRemovedServiceImpl literatureRemovedService;

    @Autowired
    public LiteratureController(LiteratureServiceImpl service, SpecialityServiceImpl specService, DisciplineServiceImpl discipService, LiteratureSearch searchService, LiteratureRemovedServiceImpl literatureRemovedService) {
        this.service = service;
        this.specService = specService;
        this.discipService = discipService;
        this.searchService = searchService;
        this.literatureRemovedService = literatureRemovedService;
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

    @GetMapping("/study/speciality")
    public String disciplinesListBySpeciality(@RequestParam Long id, Model model) {
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

    @GetMapping("/teacher/umk")
    public String umk_main(Model model) {
        model.addAttribute("tehn", specService.getSpecialitiesByDep(1L));
        model.addAttribute("arh", specService.getSpecialitiesByDep(2L));
        model.addAttribute("prom", specService.getSpecialitiesByDep(4L));
        model.addAttribute("prof", specService.getSpecialitiesByDep(5L));
        model.addAttribute("vech", specService.getSpecialitiesByDep(3L));
        model.addAttribute("zaoch", specService.getSpecialitiesByDep(6L));
        return "main_umk";
    }

    @GetMapping("/teacher/umk/speciality")
    public String umk(@RequestParam Long id, Model model) {
        model.addAttribute("generalD", discipService.getDisciplinesBySpeciality(id, 1L));
        model.addAttribute("specialD", discipService.getDisciplinesBySpeciality(id, 2L));
        model.addAttribute("specializationD", discipService.getDisciplinesBySpeciality(id, 3L));
        return "speciality_umk";
    }


    @GetMapping("/teacher/umk/discipline")
    public String umkD(Model model) {
        model.addAttribute("typeI", service.getByCategory(1L, 4L));
        model.addAttribute("typeII", service.getByCategory(5L, 8L));
        model.addAttribute("typeIII", service.getByCategory(9L, 12L));
        model.addAttribute("typeIV", service.getByCategory(13L, 17L));
        return "umk";
    }

    @GetMapping("/teacher/umk/literature")
    public String umkL(@RequestParam Long d, @RequestParam Long id, Model model) {
        model.addAttribute("literature", service.getByDisciplineAndType(d, id));
        return "lit_list1";
    }

    @GetMapping("/study/literature")
    public String studyL(@RequestParam Long id, Model model) {
        model.addAttribute("literature", service.getByDisciplineAndTypes(id));
        return "lit_list1";
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

    @GetMapping("/admin/search/literature")
    public String search(@RequestParam(required = false) String q,
                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                         Model model) {
        Page<Literature> searchResults = null;
        searchResults = searchService.search(PageRequest.of(page, 50), q);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("numbers", IntStream.range(0, searchResults.getTotalPages()).toArray());
        return "search";
    }

    @GetMapping("/admin/search/removed")
    public String searchRemoved(@RequestParam(required = false) String q,
                         @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                         Model model) {
        Page<LiteratureRemoved> searchResults = null;
        searchResults = searchService.searchRemoved(PageRequest.of(page, 50), q);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("numbers", IntStream.range(0, searchResults.getTotalPages()).toArray());
        return "search_removed";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam Long id, Model model) {
        service.insert(id);
        service.delete(id);
        return "redirect:/teacher/search/literature?q=null";
    }

    @GetMapping("/restore")
    public String restore(@RequestParam Long id, Model model) {
        literatureRemovedService.insert(id);
        literatureRemovedService.delete(id);
        return "redirect:/teacher/search/removed?q=null";
    }
}
