package com.gsc.ams;
import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.gsc.ams.controllers.PupilController;
@SpringBootApplication
public class SchoolmvcApplication {

	public static void main(String[] args) {
		new File(PupilController.uploadDirectory).mkdir();

		SpringApplication.run(SchoolmvcApplication.class, args);
	}

}
