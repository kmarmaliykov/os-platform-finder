package org.jsoftbiz.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Aurelien Broszniowski
 */

public class OSTest {

  @Test
  public void testReleaseFileWithLinuxPrettyName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
    BufferedReader mockFile = mock(BufferedReader.class);
    when(mockFile.readLine()).thenReturn("NAME=Fedora", "PRETTY_NAME=\"Fedora 17 (Beefy Miracle)\"", "VERSION_ID=17", null);

    String name = "some name";
    String version = "4.1.4";
    String arch = "68000";
    OS.OsInfo osInfo = new OS().readPlatformName(name, version, arch, mockFile);
    Assert.assertThat(osInfo.getName(), is(equalTo(name)));
    Assert.assertThat(osInfo.getVersion(), is(equalTo(version)));
    Assert.assertThat(osInfo.getArch(), is(equalTo(arch)));
    Assert.assertThat(osInfo.getPlatformName(), is(equalTo("Fedora 17 (Beefy Miracle)")));
  }

  @Test
  public void testReleaseFileWithOneLine() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
    BufferedReader mockFile = mock(BufferedReader.class);
    String line = "Fedora version 19";
    when(mockFile.readLine()).thenReturn(line, null);

    String name = "some name";
    String version = "4.1.4";
    String arch = "68000";
    OS.OsInfo osInfo = new OS().readPlatformName(name, version, arch, mockFile);
    Assert.assertThat(osInfo.getName(), is(equalTo(name)));
    Assert.assertThat(osInfo.getVersion(), is(equalTo(version)));
    Assert.assertThat(osInfo.getArch(), is(equalTo(arch)));
    Assert.assertThat(osInfo.getPlatformName(), is(equalTo(line)));
  }

  @Test
  public void testReleaseFileWithTwoLines() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
    BufferedReader mockFile = mock(BufferedReader.class);
    when(mockFile.readLine()).thenReturn("Fedora version 19", "second line", null);

    String name = "some name";
    String version = "4.1.4";
    String arch = "68000";
    OS.OsInfo osInfo = new OS().readPlatformName(name, version, arch, mockFile);
    Assert.assertThat(osInfo.getName(), is(equalTo(name)));
    Assert.assertThat(osInfo.getVersion(), is(equalTo(version)));
    Assert.assertThat(osInfo.getArch(), is(equalTo(arch)));
    Assert.assertThat(osInfo.getPlatformName(), is(equalTo("Fedora version 19")));
  }

  @Test
  public void testLsbRelease() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
    BufferedReader mockFile = mock(BufferedReader.class);
    when(mockFile.readLine()).thenReturn("DISTRIB_ID=Ubuntu", "DISTRIB_RELEASE=9.10", "DISTRIB_CODENAME=karmic",
        "DISTRIB_DESCRIPTION=\"Ubuntu 9.10\"", null);

    String name = "some name";
    String version = "4.1.4";
    String arch = "68000";
    OS.OsInfo osInfo = new OS().readPlatformNameFromLsb(name, version, arch, mockFile);
    Assert.assertThat(osInfo.getName(), is(equalTo(name)));
    Assert.assertThat(osInfo.getVersion(), is(equalTo(version)));
    Assert.assertThat(osInfo.getArch(), is(equalTo(arch)));
    Assert.assertThat(osInfo.getPlatformName(), is(equalTo("Ubuntu 9.10 (karmic)")));
  }

}
