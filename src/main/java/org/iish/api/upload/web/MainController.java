package org.iish.api.upload.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
public class MainController {
    private @Value("${api.key}") String apiKey;

    private @Value("${upload.directory}") String uploadDirectory;
    private @Value("${datestamp.directory}") String datestampDirectory;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/datestamp/{na}/{pid}", method = RequestMethod.PUT)
    public ResponseEntity<String> datestampUpdate(@PathVariable String na, @PathVariable String pid,
                                                  @RequestParam String key) throws IOException {
        if (apiKey.equals(key)) {
            String path = this.uploadDirectory + File.separator + na + File.separator + pid;
            File file = new File(path);

            file.getParentFile().mkdirs();
            if (file.exists() || file.createNewFile()) {
                return new ResponseEntity<>("OK!", HttpStatus.OK);
            }

            return new ResponseEntity<>("Failed to update datestamp", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Incorrect access token", HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String uploadForm() {
        return "upload";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Uploaded file is empty!");
            return "redirect:/";
        }

        try {
            extractZip(file.getInputStream());
        }
        catch (IOException ioe) {
            redirectAttributes.addFlashAttribute("error", "Failed to extract zip: " + ioe.getMessage());
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("success", "Successfully uploaded and extracted the zip file!");
        return "redirect:/";
    }

    private void extractZip(InputStream zipInputStream) throws IOException {
        ZipInputStream zipInput = new ZipInputStream(zipInputStream);
        ZipEntry nextEntry = zipInput.getNextEntry();

        while (nextEntry != null) {
            String path = this.uploadDirectory + File.separator + nextEntry.getName();
            if (!nextEntry.isDirectory()) {
                extractZipFile(zipInput, path);
            } else {
                File directory = new File(path);
                directory.mkdir();
            }
            zipInput.closeEntry();
            nextEntry = zipInput.getNextEntry();
        }

        zipInput.close();
    }

    private void extractZipFile(ZipInputStream zipInput, String path) throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
        byte[] bytes = new byte[4096];
        int read = 0;

        while ((read = zipInput.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }

        outputStream.close();
    }
}