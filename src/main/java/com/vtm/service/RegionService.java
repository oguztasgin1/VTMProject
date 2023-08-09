package com.vtm.service;

import com.vtm.dto.request.RegionCreateRequestDto;
import com.vtm.dto.request.RegionUpdateRequestDto;
import com.vtm.entity.Fleet;
import com.vtm.entity.Region;

import com.vtm.repository.IRegionRepository;
import com.vtm.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegionService extends ServiceManager<Region, Long> {
    private final IRegionRepository repository;

    public RegionService(IRegionRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Region createRegion(RegionCreateRequestDto dto) {
        Region region = Region.builder()
                .regionName(dto.getRegionName())
                .build();
        save(region);
        return region;
    }

    public Region getByRegionId(Long regionId) {
        Optional<Region> region = repository.findById(regionId);
        if (region.isEmpty()){
            System.out.println("Region bulunamadi");
        }
        return region.get();
    }

    public Region updateByRegionId(RegionUpdateRequestDto dto) {
        Optional<Region> region = repository.findById(dto.getRegionId());
        if (region.isEmpty()){
            System.out.println("Region bulunamadi");
        }
        region.get().setRegionName(dto.getRegionName());
        update(region.get());
        return region.get();
    }

    public Boolean deleteByRegionId(Long regionId) {
        Optional<Region> region = repository.findById(regionId);
        if (region.isEmpty()){
            System.out.println("Region bulunamadi");
        }
        delete(region.get());
        return true;
    }
}
