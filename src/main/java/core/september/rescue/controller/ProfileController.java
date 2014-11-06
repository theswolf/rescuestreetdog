package core.september.rescue.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import core.september.rescue.model.Profile;
import core.september.rescue.service.FileService;
import core.september.rescue.service.ProfileService;

@Controller
@RequestMapping("api/sec/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService ;
	@Autowired
	private FileService fileService;

	@RequestMapping(value="me")
	public @ResponseBody Map<String,Object> me(HttpServletRequest req) throws IOException {
		Map<String,Object> retMap = new HashMap<String,Object>();
		String username = (String) req.getAttribute("payload");
		Profile profile = profileService.getRepo().findOne(username);
		retMap.put("profile", profile);
		retMap.put("pics",fileService.getForOwner(username));
		return retMap;
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public @ResponseBody Profile save(@RequestBody Profile profile) throws IOException {
		Profile saved = profileService.getRepo().save(profile);
		return profile;
	}
	
	@RequestMapping(value="picture", method=RequestMethod.POST)
	public @ResponseBody boolean uploadpicture(@RequestBody MultipartFile file,HttpServletRequest req) throws IOException {
		String username = (String) req.getAttribute("payload");
		boolean ret = fileService.storeFile(file.getInputStream(), username, file.getName(),file.getContentType());
		return ret;
	}
}
