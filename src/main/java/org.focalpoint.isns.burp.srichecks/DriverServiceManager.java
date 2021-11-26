/**
 * BurpSuite JavaScript Security Extension
 * Copyright (C) 2019  Focal Point Data Risk, LLC
 * Written by: Peter Hefley
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package org.focalpoint.isns.burp.srichecks;

import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;

public class DriverServiceManager {

    private String chromeDriverFilePath;
    private ChromeDriverService service;

    public DriverServiceManager(){
        startDriverService();
    }


    public void startDriverService(){
        try{
            // https://seleniumhq.github.io/selenium/docs/api/java/
            ChromeDriverService.Builder builder = new ChromeDriverService.Builder().usingAnyFreePort();
            if (chromeDriverFilePath != null) {
                builder.usingDriverExecutable(new File(chromeDriverFilePath));
            }

            service = builder.build();
            service.start();
        }
        catch (IOException e){
            System.err.println("[JS-SRI][-] Could not start chromedriver service");
        }
    }

    public void stopDriverService(){
        service.stop();
    }

    public ChromeDriverService getService(){
        return service;
    }

    private void reloadIfRunning(){
        if (service.isRunning()){
            // Restart it
            stopDriverService();
            startDriverService();
        }
    }

    public void setDriverPath(String path){
        chromeDriverFilePath = path;
        reloadIfRunning();
    }

}
