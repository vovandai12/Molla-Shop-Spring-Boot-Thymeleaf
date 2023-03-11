package com.example.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.BrandDto;
import com.example.model.Brand;
import com.example.service.BrandService;
import com.example.service.SessionService;

@Controller
@RequestMapping(value = "brands")
public class BrandController {

	@Autowired
	BrandService brandService;

	@Autowired
	SessionService session;

	@GetMapping(value = "")
	public String list(Model model, @RequestParam(name = "field") Optional<String> field,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "keywords", defaultValue = "") Optional<String> keywords) {
		String keyword = keywords.orElse(session.get("keywords"));
		session.set("keywords", keyword);
		Sort sort = Sort.by(Direction.DESC, field.orElse("id"));
		Pageable pageable = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);
		Page<Brand> resultPage = brandService.findAllByNameLike("%" + keyword + "%", pageable);
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
		return "admin/brands/brand-list";
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> deleteApi(@PathVariable(name = "id") Long id) throws IOException {
		brandService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping(value = "/view/{id}")
	public ResponseEntity<Brand> viewApi(@PathVariable(name = "id") Long id) {
		Optional<Brand> brand = brandService.findById(id);
		return new ResponseEntity<Brand>(brand.get(), HttpStatus.OK);
	}

	@GetMapping(value = "/saveOrUpdate")
	public String saveOrUpdate(Model model, @ModelAttribute("brandDto") BrandDto brandDto) {
		return "admin/brands/brand-form";
	}

	@GetMapping(value = "/edit")
	public String saveOrUpdateId(Model model, @RequestParam(value = "id") Long id) {
		Brand brand = brandService.findById(id).get();
		BrandDto brandDto = new BrandDto();
		brandDto.setId(id);
		brandDto.setName(brand.getName());
		model.addAttribute("brandDto", brandDto);
		return "admin/brands/brand-form";
	}

	@PostMapping(value = "/saveOrUpdate/submit")
	public String saveOrUpdate(Model model, @Valid @ModelAttribute("brandDto") BrandDto brandDto,
			BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "admin/brands/brand-form";
		}
		Brand brand;
		if (brandDto.getId() != null) {
			brand = brandService.findById(brandDto.getId()).get();
			brand.setName(brandDto.getName());
		} else {
			brand = new Brand();
			brand.setName(brandDto.getName());
		}
		brandService.saveOrUpdate(brand);
		return "redirect:/brands";
	}

}
