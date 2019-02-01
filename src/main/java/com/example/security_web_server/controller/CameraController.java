package com.example.security_web_server.controller;

import com.example.security_web_server.controller.controller_resource.AccessLevel;
import com.example.security_web_server.dao.CameraDAO;
import com.example.security_web_server.dao.CameraModuleDAO;
import com.example.security_web_server.dao.ScheduleDAO;
import com.example.security_web_server.dao.ModuleDAO;
import com.example.security_web_server.dao.impl.UserDetailsServiceImpl;
import com.example.security_web_server.model.Camera;
import com.example.security_web_server.model.CameraModule;
import com.example.security_web_server.model.Identity.CameraModuleIdentity;
import com.example.security_web_server.model.Module;
import com.example.security_web_server.model.Schedule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/camera")
public class CameraController {

  private int pid = 1;
  @Autowired
  private CameraDAO myRepo;
  @Autowired
  private CameraModuleDAO cmRepo;
  @Autowired
  private ScheduleDAO scRepo;
  @Autowired
  private ModuleDAO mRepo;
  @Autowired
  private UserDetailsServiceImpl userDetailsRepo;

  @GetMapping("/getAll")
  @Transactional
  public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ.level());
	if (check) {
	  List<Camera> returnList = myRepo.findAll();
	  System.out.println(returnList);
	  return new ResponseEntity<>(returnList, HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @GetMapping("/getAllByLocationID/{id}")
  @Transactional
  public ResponseEntity<?> getAll(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ.level());
	if (check) {
	  List<Camera> returnList = myRepo.findAll().stream().filter(cam -> cam.getLocation_id() == id).collect(Collectors.toList());
	  System.out.println(returnList);
	  return new ResponseEntity<>(returnList, HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @GetMapping(path="/get/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  public ResponseEntity<?> getByID(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ.level());
	if (check) {
	  Camera cam = myRepo.findById(id).orElse(null);
    JSONObject jCamData = new JSONObject();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
  	  String myString = null;
  	  try {
  		    myString = objectMapper.writeValueAsString(cam);
  	  } catch (JsonProcessingException e) {
  		    e.printStackTrace();
  	  }
      JSONObject camJSON = new JSONObject(myString);
      jCamData.put("camera", camJSON);
    } catch(Exception e) {
      e.printStackTrace();
    }

    int camID = cam.getCamera_id();

    // Get list of modules
    List<Module> moduleList = mRepo.findAll();
    // Initialise list of camera modules
	  ArrayList<CameraModule> cameraModuleList = new ArrayList<>();
    CameraModule camModule;
    CameraModuleIdentity camModIdentity;
    for (Module m : moduleList) {
      camModIdentity = new CameraModuleIdentity(camID, m.getModule_id());
      camModule = cmRepo.findById(camModIdentity).orElse(null);
      if(camModule != null) {
  		    cameraModuleList.add(camModule);
      }
    }
    jCamData.put("camModuleList", cameraModuleList);

    //Get list of schedules
    List<Schedule> scheduleList = scRepo.findAll().stream().filter(sch -> sch.getCam_id() == camID).collect(Collectors.toList());
    jCamData.put("camSchedList", scheduleList);
    System.out.println(jCamData.toString());
	  return new ResponseEntity<>(jCamData.toString(), HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @PostMapping("/add")
  @Transactional
  public ResponseEntity<?> add(@RequestBody CameraDetails cameraDetails, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
	if (check) {
	  JSONObject jCamData = new JSONObject();
	  Camera savedCamera = myRepo.save(cameraDetails.camera);
	  ObjectMapper objectMapper = new ObjectMapper();
	  String myString = null;
	  try {
		    myString = objectMapper.writeValueAsString(savedCamera);
	  } catch (JsonProcessingException e) {
		    e.printStackTrace();
	  }
	  jCamData.put("camera", myString);
	  int camID = savedCamera.getCamera_id();

	  // Initialise list of camera modules
	  ArrayList<CameraModule> cameraModuleList = new ArrayList<>();
	  CameraModule camModule;
	  // Loop and add to list
	  for (Module m : cameraDetails.moduleList) {
		camModule = new CameraModule(new CameraModuleIdentity(camID, m.getModule_id()));
		cameraModuleList.add(camModule);
	  }
	  //Save all to camera_module table
	  cmRepo.saveAll(cameraModuleList);
	  jCamData.put("camModuleList", cameraModuleList);
	  // Initialise list of camera schedules
	  ArrayList<Schedule> cameraScheduleList = new ArrayList<>();
	  Schedule camSchedule;
	  // Loop and add camID to list
	  for (Schedule s : cameraDetails.scheduleList) {
		s.setCam_id(camID);
		camSchedule = s;
		cameraScheduleList.add(camSchedule);
	  }
	  //Save all to schedule table
	  scRepo.saveAll(cameraScheduleList);
	  jCamData.put("camSchedList", cameraScheduleList);

	  // Initialise the camera instance in Flask server
	  HttpHeaders headers = new HttpHeaders();
	  headers.setContentType(MediaType.APPLICATION_JSON);
	  HttpEntity<String> entity = new HttpEntity<String>(jCamData.toString(), headers);
	  RestTemplate restTemplate = new RestTemplate();
	  ResponseEntity<String> result = restTemplate.exchange("http://192.168.1.183:5000/api/v1/newcamera/", HttpMethod.POST, entity, String.class);

	  return new ResponseEntity<>(jCamData, HttpStatus.CREATED);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @PutMapping("/edit")
  @Transactional
  public ResponseEntity<?> update(@RequestBody CameraDetails cameraDetails, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
	if (check) {
    JSONObject jCamData = new JSONObject();
	  Camera savedCamera = myRepo.save(cameraDetails.camera);
	  ObjectMapper objectMapper = new ObjectMapper();
	  String myString = null;
	  try {
		myString = objectMapper.writeValueAsString(savedCamera);
	  } catch (JsonProcessingException e) {
		e.printStackTrace();
	  }
	  jCamData.put("camera", myString);
	  int camID = savedCamera.getCamera_id();

	  // Initialise list of camera modules
	  ArrayList<CameraModule> cameraModuleList = new ArrayList<>();
	  CameraModule camModule;
	  // Loop and add to list
	  for (Module m : cameraDetails.moduleList) {
		camModule = new CameraModule(new CameraModuleIdentity(camID, m.getModule_id()));
		cameraModuleList.add(camModule);
	  }
	  //Save all to camera_module table
	  cmRepo.saveAll(cameraModuleList);
	  jCamData.put("camModuleList", cameraModuleList);
	  // Initialise list of camera schedules
	  ArrayList<Schedule> cameraScheduleList = new ArrayList<>();
	  Schedule camSchedule;
	  // Loop and add camID to list
	  for (Schedule s : cameraDetails.scheduleList) {
		s.setCam_id(camID);
		camSchedule = s;
		cameraScheduleList.add(camSchedule);
	  }
	  //Save all to schedule table
	  scRepo.saveAll(cameraScheduleList);
	  jCamData.put("camSchedList", cameraScheduleList);

	  // Initialise the camera instance in Flask server
	  HttpHeaders headers = new HttpHeaders();
	  headers.setContentType(MediaType.APPLICATION_JSON);
	  HttpEntity<String> entity = new HttpEntity<String>(jCamData.toString(), headers);
	  RestTemplate restTemplate = new RestTemplate();
	  ResponseEntity<String> result = restTemplate.exchange("http://192.168.1.183:5000/api/v1/newcamera/", HttpMethod.POST, entity, String.class);
	  return new ResponseEntity<>(jCamData ,HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  @DeleteMapping(value = "/delete/{id}")
  @Transactional
  public ResponseEntity<?> deleteByID(@PathVariable("id") int id, @RequestBody List<CameraModule> cameraModuleList, @RequestHeader("Authorization") String token) {
	// Check if user is able to access resource
	Boolean check = userDetailsRepo.checkAccess(token, pid, AccessLevel.READ_WRITE.level());
	if (check) {
	  myRepo.deleteById(id);
	  cmRepo.deleteAll(cameraModuleList);
	  return new ResponseEntity<>(HttpStatus.OK);
	} else {
	  return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
  }

  static class CameraDetails {
	Camera camera;
	List<Module> moduleList;
	List<Schedule> scheduleList;

	public Camera getCamera() {
	  return camera;
	}

	public void setCamera(Camera camera) {
	  this.camera = camera;
	}

	public List<Module> getModuleList() {
	  return moduleList;
	}

	public void setModuleList(List<Module> moduleList) {
	  this.moduleList = moduleList;
	}

	public List<Schedule> getScheduleList() {
	  return scheduleList;
	}

	public void setScheduleList(List<Schedule> scheduleList) {
	  this.scheduleList = scheduleList;
	}
  }
}
