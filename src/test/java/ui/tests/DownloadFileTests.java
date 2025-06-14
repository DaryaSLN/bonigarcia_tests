package ui.tests;

import ui.constants.Constants;
import org.junit.jupiter.api.Test;
import ui.pageObjects.DownloadFilesPage;
import ui.pageObjects.HomePage;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class DownloadFileTests extends BaseTest {
    @Test
    void DownloadPngFileTest() {
        File pngFile = new HomePage(getDriver())
                .navigateToPage(Constants.DOWNLOAD_PAGE_LINKTEXT, DownloadFilesPage.class)
                .downloadPng();

        assertThat(pngFile).exists();
    }

    @Test
    void DownloadPdfFileTest() {
        File pdfFile = new HomePage(getDriver())
                .navigateToPage(Constants.DOWNLOAD_PAGE_LINKTEXT, DownloadFilesPage.class)
                .downloadPdf();

        assertThat(pdfFile).exists();
    }
}
