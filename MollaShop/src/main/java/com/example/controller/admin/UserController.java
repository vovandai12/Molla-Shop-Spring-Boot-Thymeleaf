package com.example.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.exception.StorageFileNotFoundException;
import com.example.model.User;
import com.example.service.SessionService;
import com.example.service.StorageService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	SessionService session;

	@GetMapping(value = "")
	public String list(Model model, 
			@RequestParam(name = "field") Optional<String> field,
			@RequestParam(name = "page") Optional<Integer> page,
			@RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "keywords", defaultValue = "") Optional<String> keywords) {
		String keyword = keywords.orElse(session.get("keywords"));
		session.set("keywords", keyword);
		Sort sort = Sort.by(Direction.DESC, field.orElse("id"));
		Pageable pageable = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);
		Page<User> resultPage = userService.findAllByUsernameLike("%" + keyword + "%", pageable);
		int totalPages = resultPage.getTotalPages();
		int startPage = Math.max(1, page.orElse(1) - 2);
		int endPage = Math.min(page.orElse(1) + 2, totalPages);
		if (totalPages > 5) {
			if (endPage == totalPages)
				startPage = endPage - 5;
			else if (startPage == 1)
				endPage = startPage + 5;
		}
		List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage).boxed().collect(Collectors.toList());
		model.addAttribute("pageNumbers", pageNumbers);
		model.addAttribute("field", field.orElse("id"));
		model.addAttribute("size", size.orElse(5));
		model.addAttribute("keywords", keyword);
		model.addAttribute("resultPage", resultPage);
		return "admin/users/user-list";
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteApi(@PathVariable(name = "id") String id) throws IOException {
		System.out.println(id);
//		Optional<User> user = userService.findById(id);
//		if (user.get().getAvatar() != null)
//			storageService.delete(user.get().getAvatar());
//		userService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
	
}
