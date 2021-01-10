import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

	public static String PATH = "..//javacore1.3.1//Games//savegames//";

	public static void main(String[] args) {
		GameProgress gameProgress1 = new GameProgress(100, 60, 5, 105.2);
		GameProgress gameProgress2 = new GameProgress(95, 75, 7, 120.7);
		GameProgress gameProgress3 = new GameProgress(80, 45, 3, 80.5);


		saveGame("save1.dat", gameProgress1);
		saveGame("save2.dat", gameProgress2);
		saveGame("save3.dat", gameProgress3);

		zipFiles("zip.zip", new String[]{"save1.dat", "save2.dat", "save3.dat"});

	}

	public static void saveGame(String nameFiles, GameProgress gameProgress) {
		File save = new File(PATH, nameFiles);
		try {
			if (save.createNewFile()) {
				System.out.printf("Файл %s в папке savegames успешно создан.\n", nameFiles);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		String path = PATH + nameFiles;

		try (FileOutputStream fos = new FileOutputStream(path);
		     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(gameProgress);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void zipFiles(String zipFile, String[] files) {
		File save = new File(PATH, zipFile);
		try {
			if (save.createNewFile()) {
				System.out.printf("Файл %s в папке savegames успешно создан.\n", zipFile);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		String path = PATH + zipFile;

		try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
			for (String file : files) {
				String p = PATH + file;
				FileInputStream fis = new FileInputStream(p);
				ZipEntry entry = new ZipEntry(file);
				try {
					zout.putNextEntry(entry);
					byte[] buffer = new byte[fis.available()];
					fis.read(buffer);
					zout.write(buffer);
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				fis.close();
			}
			deleteFiles(files);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void deleteFiles(String[] files) {
		for (int i = 0; i < files.length; i++) {
			File file = new File(PATH, files[i]);
			if (file.delete()) {
				System.out.printf("Файл %s удален\n", files[i]);
			}
		}
	}
}


