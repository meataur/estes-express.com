package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.BillOfLading.web.dto.Commodity;
import com.estes.myestes.BillOfLading.web.dto.GoodsType;
import com.estes.myestes.BillOfLading.web.dto.ShipmentClass;

public class CommodityMapper implements RowMapper<Commodity> {

	@Override
	public Commodity mapRow(ResultSet rs, int rowNum) throws SQLException {
		int commodityId = rs.getInt("COMMODITY_ID");
		String hazmat = rs.getString("HAZMAT").trim();
		int goodsUnit = 0;
		/**
		 * Goods Unit & weight columns for Draft & Template are non-numeric
		 */
		try{
			goodsUnit = rs.getInt("GOODS_UNIT");
		}catch(Exception ex){
			try{
				goodsUnit = Integer.parseInt(rs.getString("GOODS_UNIT").replaceAll("\\D+", ""));
			}catch(Exception e){
				ESTESLogger.log(ESTESLogger.DEBUG, getClass(),"mapRow()","Could not convert Goods unit into integer");
			}
		}
		String goodsType = rs.getString("GOODS_TYPE").trim();
		
		int goodsWeight=0;
		
		try{
			goodsWeight = rs.getInt("GOODS_WEIGHT");
		}catch(Exception ex){
			try{
				goodsWeight = Integer.parseInt(rs.getString("GOODS_WEIGHT").replaceAll("\\D+", ""));

			}catch(Exception e){
				ESTESLogger.log(ESTESLogger.DEBUG, getClass(),"mapRow()","Could not convert Goods weight into integer");

			}
		}

		String shipmentClass = rs.getString("SHIPMENT_CLASS").trim();
		String nmfc = rs.getString("NMFC").trim();
		String nmfcExt = rs.getString("NMFC_EXT").trim();

		String	numberOfPackage = rs.getString("NUMBER_OF_PACKAGE").trim();

		String packageType = rs.getString("PACKAGE_TYPE").trim();
		String description = rs.getString("DESCRIPTION").trim();
		String height = rs.getString("HEIGHT").trim();
		String weight = rs.getString("WEIGHT").trim();
		String length = rs.getString("LENGTH").trim();

		
		Commodity commodity = new Commodity();
		
		commodity.setCommodityId(commodityId);
		commodity.setHazmat(hazmat.equalsIgnoreCase("Y"));
		commodity.setGoodsUnit(goodsUnit);
		commodity.setGoodsType(new GoodsType(goodsType));
		commodity.setGoodsWeight(goodsWeight);
		commodity.setShipmentClass(new ShipmentClass(shipmentClass));
		commodity.setNmfc(nmfc);
		commodity.setNmfcExt(nmfcExt);
		commodity.setNumberOfPackage(numberOfPackage);
		commodity.setPackageType(packageType);
		commodity.setDescription(description);
		commodity.setHeight(height);
		commodity.setWeight(weight);
		commodity.setLength(length);

		return commodity;
	}

}
