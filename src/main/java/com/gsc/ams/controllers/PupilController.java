package com.gsc.ams.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gsc.ams.entities.Teacher;
import com.gsc.ams.entities.Pupil;
import com.gsc.ams.repositories.PupilRepository;
import com.gsc.ams.repositories.TeacherRepository;

import jakarta.persistence.criteria.Path;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/pupil/")
public class PupilController {
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
	private final PupilRepository pupilRepository;
	private final TeacherRepository teacherRepository;

	@Autowired
	public PupilController(PupilRepository pupilRepository, TeacherRepository teacherRepository) {
		this.pupilRepository = pupilRepository;
		this.teacherRepository = teacherRepository;
	}

	@GetMapping("list")
	public String listTeachers(Model model) {
		// model.addAttribute("articles", null);
		List<Pupil> la = (List<Pupil>) pupilRepository.findAll();
		if (la.size() == 0)
			la = null;
		model.addAttribute("pupils", la);
		return "pupil/listPupils";
	}

	@GetMapping("add")
	public String showAddArticleForm(Pupil article, Model model) {
		model.addAttribute("teachers", teacherRepository.findAll());
		model.addAttribute("pupil", new Pupil());
		return "pupil/addPupil";
	}

	@PostMapping("add")
	// @ResponseBody
	public String addPupil(@Valid Pupil pupil, BindingResult result,
			@RequestParam(name = "teacherId", required = false) Long p, @RequestParam("files") MultipartFile[] files) {
		Teacher teacher = teacherRepository.findById(p)
				.orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + p));
		pupil.setTeacher(teacher);
		StringBuilder fileName = new StringBuilder();
		MultipartFile file = files[0];

		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");
		String var = ldt.format(format);

		java.nio.file.Path fileNameAndPath = Paths.get(uploadDirectory, var + "_" + file.getOriginalFilename());

		fileName.append(var + "_" + file.getOriginalFilename());
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		pupil.setPicture(fileName.toString());

		pupilRepository.save(pupil);
		return "redirect:list";
		// return article.getLabel() + " " +article.getPrice() + " " + p.toString();
	}

	@GetMapping("delete/{id}")
	public String deleteTeacher(@PathVariable("id") long id, Model model) {
		Pupil pupil = pupilRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + id));
		pupilRepository.delete(pupil);
		model.addAttribute("pupils", pupilRepository.findAll());
		return "pupil/listPupils";
	}

	@GetMapping("edit/{id}")
	public String showPupilFormToUpdate(@PathVariable("id") long id, Model model) {
		Pupil pupil = pupilRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + id));
		model.addAttribute("pupil", pupil);
		model.addAttribute("teachers", teacherRepository.findAll());
		model.addAttribute("idTeacher", pupil.getTeacher().getId());
		return "pupil/updatePupil";
	}

	@PostMapping("edit/{id}")
	public String updatePupil(@PathVariable("id") long id, @Valid Pupil pupil, BindingResult result, Model model,
			@RequestParam(name = "teacherId", required = false) Long p) {
		if (result.hasErrors()) {
			pupil.setId(id);
			return "pupil/updatePupil";
		}
		Teacher teacher = teacherRepository.findById(p)
				.orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + p));
		pupil.setTeacher(teacher);
		pupilRepository.save(pupil);
		model.addAttribute("pupils", pupilRepository.findAll());
		return "pupil/listPupils";
	}

	@GetMapping("show/{id}")
	public String showPupilDetails(@PathVariable("id") long id, Model model) {
		Pupil pupil = pupilRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid pupil Id:" + id));

		model.addAttribute("pupil", pupil);

		return "pupil/showPupil";
	}

}

