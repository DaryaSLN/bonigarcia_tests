package tests;

import constants.Constants;
import org.junit.jupiter.api.Test;
import pageObjects.DownloadFilesPage;
import pageObjects.HomePage;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class DownloadFileTests extends BaseTest {
    @Test
    void DownloadPngFileTest() {
        File pngFile = new HomePage(getDriver())
                .navigateToPage(Constants.DOWNLOAD_PAGE_PATH, DownloadFilesPage.class)
                .downloadPng();

        assertThat(pngFile).exists();
    }

    @Test
    void DownloadPdfFileTest() {
        File pdfFile = new HomePage(getDriver())
                .navigateToPage(Constants.DOWNLOAD_PAGE_PATH, DownloadFilesPage.class)
                .downloadPdf();

        assertThat(pdfFile).exists();
    }
}
