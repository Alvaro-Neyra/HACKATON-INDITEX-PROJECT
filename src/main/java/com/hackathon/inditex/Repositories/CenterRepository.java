package com.hackathon.inditex.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.models.center.CenterListDTO;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    @Query("SELECT c FROM Center c WHERE c.name = :name")
    public List<Center> findCenterByName(@Param("name") String name);
    @Query("SELECT new com.hackathon.inditex.models.center.CenterListDTO(c.id, c.name, c.capacity, c.status, c.currentLoad, c.maxCapacity, c.coordinates) FROM Center c")
    public List<CenterListDTO> listCenters();    
    @Query("SELECT new com.hackathon.inditex.models.center.CenterListDTO(c.id, c.name, c.capacity, c.status, c.currentLoad, c.maxCapacity, c.coordinates) FROM Center c WHERE c.id = :id")
    public CenterListDTO getCenterById(@Param("id") Long id);
    @Query("SELECT c FROM Center c WHERE c.coordinates.latitude = :latitude AND c.coordinates.longitude = :longitude")
    public List<Center> findCenterByCoordinates(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
    @Query("SELECT c FROM Center c WHERE c.status = :status")
    public List<Center> findCenterByStatus(@Param("status") String status);
}
