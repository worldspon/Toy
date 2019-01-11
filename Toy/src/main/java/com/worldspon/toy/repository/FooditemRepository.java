package com.worldspon.toy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.worldspon.toy.entity.Fooditem;

public interface FooditemRepository extends JpaRepository<Fooditem, Long> {
	/**
	 * Fooditem 엔티티 클래스 JPA
	 * */
	@Query(value="SELECT " + 
			"i.fid, " + 
			"i.created_date, " +
			"i.modified_date, " + 
			"i.foodname, " + 
			"i.foodprice, " + 
			"i.status, " + 
			"i.stock, " + 
			"f.imgfilename, " + 
			"f.orgfilename " + 
			"FROM FOODITEM AS i " + 
			"JOIN FOODIMGFILE AS f " + 
			"ON i.fid = f.fid",
			nativeQuery = true)
	public List<Fooditem> findfood();
}
