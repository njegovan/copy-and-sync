package dev.njegovan.copyandsync;

import dev.njegovan.copyandsync.service.FileCopyingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

@Log4j2
@SpringBootApplication
public class CopyAndSyncApplication implements CommandLineRunner {

    private static final int EXIT_FLAG = 0;

    private final FileCopyingService fileCopyingService;

    public CopyAndSyncApplication(FileCopyingService fileCopyingService) {
        this.fileCopyingService = fileCopyingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CopyAndSyncApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int[] option = new int[1];

        do {
            System.out.println("Options: \n0 -> to exit; \n1 -> to continue;\n**********************************************************************");
            System.out.print("Choose your option (0 or 1):");
            try {
                Scanner scanner = new Scanner(System.in);
                option[0] = scanner.nextInt();

                if (option[0] == 1) {
                    fileCopyingService.execute();
                    System.out.println("File copying process finished successfully");
                }
            } catch (InputMismatchException e) {
                System.out.println("You have entered the wrong option value. Please try again.");
                option[0] = 100;
            } catch (FileNotFoundException e) {
                System.out.println("You have entered the wrong file path. Please try again.");
                option[0] = 101;
            }
            System.out.println("----------------------------------------------------------------------");
        } while (option[0] != EXIT_FLAG);
    }
}
