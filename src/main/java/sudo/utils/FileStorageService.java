package sudo.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
	private String uploadDir; // Configure in application.properties

	public String save(MultipartFile file, String baseDir) {
		if (baseDir != null && !baseDir.isEmpty()) {
			uploadDir =baseDir;
		}
		try {
			Path targetDirectory = Paths.get(uploadDir);

			// Create directory if it doesn't exist
			if (!Files.exists(targetDirectory)) {
				Files.createDirectories(targetDirectory);
			}

			// Build file name
			String originalFilename = file.getOriginalFilename();
			String fileName = System.currentTimeMillis() + "_" + originalFilename;

			// Define full path
			Path filePath = targetDirectory.resolve(fileName);

			// Save file
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			// Return relative path or public URL
			return fileName;

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to store file: " + e.getMessage());
		}
	}
}
