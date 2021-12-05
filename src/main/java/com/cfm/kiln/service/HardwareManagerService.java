package com.cfm.kiln.service;

import com.cfm.kiln.hardware.proxy.Fan;
import com.cfm.kiln.hardware.proxy.Heater;
import com.cfm.kiln.hardware.proxy.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareManagerService {
    private List<Device> fans;
    private List<Device> heaters;
    Logger log = LoggerFactory.getLogger(this.getClass());

    public HardwareManagerService() {
        this.fans = new ArrayList<>();
        this.heaters = new ArrayList<>();
    }

    public void addFan(Fan fan) {
        fans.add(fan);
    }

    public void addHeater(Heater heater) {
        heaters.add(heater);
    }

    public void runFans(int load) {
        if(calculateLoad(fans) == load) return;
        int fansToLoad = calculateDevicesForLoad(fans, load);
        log.info("Loading {} fans out of {}", fansToLoad, fans.size());
        runDevices(fans, fansToLoad);
        stopDevices(fans, fans.size() - fansToLoad);
    }

    public void stopFans() {
        if(calculateLoad(fans) == 0) return;
        log.debug("Stop all fans");
        fans.forEach(Device::stop);
    }

    public void runHeaters(int load) {
        if(calculateLoad(heaters) == load) return;
        int heatersToLoad = calculateDevicesForLoad(heaters, load);
        log.info("Loading {} heaters out of {}", heatersToLoad, heaters.size());
        runDevices(heaters, heatersToLoad);
        stopDevices(heaters, heaters.size() - heatersToLoad);
    }

    public void stopHeaters() {
        if(calculateLoad(heaters) == 0) return;
        log.debug("Stop all heaters");
        heaters.forEach(Device::stop);
    }

    public int getFanLoad() {
        return calculateLoad(fans);
    }

    public int getHeaterLoad() {
        return calculateLoad(heaters);
    }


    private int calculateLoad(List<Device> devices) {
        return (int) Math.ceil(devices.stream().filter(Device::isRunning).count() * 100d / (double) devices.size());
    }

    private int calculateDevicesForLoad(List<Device> devices, int load) {
        return (int) Math.ceil(devices.size() * load / 100d);
    }

    private void runDevices(List<Device> devices, int devicesToLoad) {
        for(int i = 0; i < devicesToLoad; i++) {
            devices.get(i).start();
        }
    }

    private void stopDevices(List<Device> devices, int devicesToStop) {
        for(int i = 0; i < devicesToStop; i++) {
            devices.get(i).stop();
        }
    }
}
