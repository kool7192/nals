package com.example.demo.api;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Work;
import com.example.demo.service.WorkService;

@RestController
@RequestMapping("/api")
public class RestApiController {
	public static Logger logger = LoggerFactory.getLogger(RestApiController.class);

	private List<String> arrStatus = Arrays.asList("Planning", "Doing", "Complete");
	
	@Autowired
	WorkService workService;

	@RequestMapping(value = "/work", method = RequestMethod.POST)
	public ResponseEntity saveWork(@Valid @RequestBody Work work) {
		String status = work.getStatus();
		if(status == null || arrStatus.indexOf(status) < 1) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Status not vaild");
		}
		return ResponseEntity.ok(workService.save(work));
	}

	@RequestMapping(value = "/work/{id}", method = RequestMethod.PUT)
	public ResponseEntity updateWork(@PathVariable(value = "id") Long id, @Valid @RequestBody Work workForm) {

		Work work = workService.getOne(id);
		if (work == null) {
			return ResponseEntity.notFound().build();
		}
		String status = workForm.getStatus();
		if(status == null || arrStatus.indexOf(status) < 1) {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Status not vaild");
		}
		work.setName(workForm.getName());
		work.setStart(workForm.getStart());
		work.setEnd(workForm.getEnd());
		work.setStatus(workForm.getStatus());
		Work updatedWork = workService.save(work);
		return ResponseEntity.ok(updatedWork);
	}

	@RequestMapping(value = "/work/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Work> deleteWork(@PathVariable(value = "id") Long id) {
		Work work = workService.getOne(id);
		if (work == null) {
			return ResponseEntity.notFound().build();
		}

		workService.delete(work);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/work", method = RequestMethod.GET)
	public ResponseEntity<Page<Work>> listAllWorkPagging(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@RequestParam(name = "sortName", required = false) String sortName,
			@RequestParam(name = "sortStarting", required = false) String sortStarting,
			@RequestParam(name = "sortEnding", required = false) String sortEnding,
			@RequestParam(name = "sortStatus", required = false) String sortStatus) {
		Sort sortWork = null;
		
		// sort by Name
		if (sortName.equals("ASC")) {
			sortWork = Sort.by("name").ascending();
		} else if (sortName.equals("DESC")) {
			sortWork = Sort.by("name").descending();
		}

		// sort by starting date
		if (sortStarting.equals("ASC")) {
			if (sortWork == null) {
				sortWork = Sort.by("start").ascending();
			} else {
				sortWork = sortWork.and(Sort.by("start").ascending());
			}
		} else if (sortStarting.equals("DESC")) {
			if (sortWork == null) {
				sortWork = Sort.by("start").descending();
			} else {
				sortWork = sortWork.and(Sort.by("start").descending());
			}
		}

		// sort by ending date
		if (sortEnding.equals("ASC")) {
			if (sortWork == null) {
				sortWork = Sort.by("end").ascending();
			} else {
				sortWork = sortWork.and(Sort.by("end").ascending());
			}
		} else if (sortEnding.equals("DESC")) {
			if (sortWork == null) {
				sortWork = Sort.by("end").descending();
			} else {
				sortWork = sortWork.and(Sort.by("end").descending());
			}
		}

		// sort by status
		if (sortStatus.equals("ASC")) {
			if (sortWork == null) {
				sortWork = Sort.by("status").ascending();
			} else {
				sortWork = sortWork.and(Sort.by("status").ascending());
			}
		} else if (sortStatus.equals("DESC")) {
			if (sortWork == null) {
				sortWork = Sort.by("status").descending();
			} else {
				sortWork = sortWork.and(Sort.by("status").descending());
			}
		}

		//default sort
		if(sortWork == null) {
			sortWork = Sort.by("name").ascending();
		}
		
		Pageable pageable = PageRequest.of(page, size, sortWork);
		Page<Work> listWork = workService.getListWork(pageable);
		if (listWork.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Page<Work>>(listWork, HttpStatus.OK);
	}
}
