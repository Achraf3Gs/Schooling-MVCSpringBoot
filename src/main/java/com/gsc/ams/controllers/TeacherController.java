package com.gsc.ams.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gsc.ams.entities.Pupil;
import com.gsc.ams.entities.Teacher;
import com.gsc.ams.repositories.TeacherRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/teacher/")
public class TeacherController {
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
	private final TeacherRepository teacherRepository;

	@Autowired
	public TeacherController(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	@GetMapping("list")
	// @ResponseBody
	public String listProviders(Model model) {
	
		List<Teacher> lp = (List<Teacher>) teacherRepository.findAll();
		if (lp.size() == 0)
			lp = null;
		model.addAttribute("teachers", lp);
		return "teacher/listTeachers";
		
	}

	@GetMapping("add")
	public String showAddProviderForm(Model model) {
		Teacher teacher = new Teacher(); // object dont la valeur des attributs par defaut
		model.addAttribute("teacher", teacher);
		return "teacher/addTeacher";
	}

	@PostMapping("add")
	public String addTeacher(@Valid Teacher teacher, BindingResult result, Model model,@RequestParam("files") MultipartFile[] files) {
		if (result.hasErrors()) {
			return "teacher/addTeacher";
		}
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

		teacher.setPicture(fileName.toString());

		teacherRepository.save(teacher);
		return "redirect:list";
	}

	@GetMapping("delete/{id}")
	public String deleteTeacher(@PathVariable("id") long id, Model model) {
		
		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + id));
		System.out.println("suite du programme...");
		teacherRepository.delete(teacher);
		/*
		 * model.addAttribute("providers", teacherRepository.findAll()); return
		 * "provider/listProviders";
		 */
		return "redirect:../list";
	}

	@GetMapping("edit/{id}")
	public String showTeacherFormToUpdate(@PathVariable("id") long id, Model model) {
		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + id));
		model.addAttribute("teacher", teacher);
		return "teacher/updateTeacher";
	}

	@PostMapping("update")
	public String updateTeacher(@Valid Teacher teacher, BindingResult result, Model model) {
		teacherRepository.save(teacher);
		return "redirect:list";
	}

	@GetMapping("show/{id}")
	public String showTeacher(@PathVariable("id") long id, Model model) {
		Teacher teacher = teacherRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid teacher Id:" + id));
		List<Pupil> pupils = teacherRepository.findArticlesByTeacher(id);
		for (Pupil a : pupils)
			System.out.println("Pupil = " + a.getLabel());
		model.addAttribute("pupils", pupils);
		model.addAttribute("teacher", teacher);
		return "teacher/showTeacher";

	}
}