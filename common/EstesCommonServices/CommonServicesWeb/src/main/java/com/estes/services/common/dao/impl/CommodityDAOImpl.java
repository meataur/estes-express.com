/**
 * @author: Lakshman K
 *
 * Creation date: 12/5/2018
 */
package com.estes.services.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.services.common.dao.iface.CommodityDAO;
import com.estes.services.common.dto.PackageType;
import com.estes.services.common.exception.ServiceException;

@Repository ("commodityDAO")
public class CommodityDAOImpl implements CommodityDAO
{
	@Autowired
	private JdbcTemplate jdbcCommonServices;

	private static final class PackageTypeMapper implements RowMapper<PackageType>
	{
		@Override
		public PackageType mapRow(ResultSet rs, int rowNm) throws SQLException {
			PackageType elem = new PackageType();
			elem.setCode(rs.getString("tbdak1").trim());
			elem.setAbbrev(rs.getString("tbdad1").trim());
			elem.setDescription(rs.getString("tbdad2").trim());
			elem.setPlural(rs.getString("tbdad3").trim());
			return elem;
		}
	} // PackageTypeMapper
	
	@Override
	public List<String> getClasses() throws ServiceException
	{
		String sql = "SELECT tbdad1 FROM fbfiles.scp002 WHERE TBDKEY = '$$CLS' ORDER BY tbdnk1";

		try {
			List<String> shipClassList = new ArrayList<String>();
			List<String> classList = jdbcCommonServices.queryForList(sql, String.class, new Object[] {});
			for(String shipClass: classList) {
				shipClassList.add(shipClass.trim());
			}
			return shipClassList;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityDAOImpl.class, "getClasses()", "** No classes found", e);
			throw new ServiceException("Could not retrieve classes.", e);
		}
	} // getClasses

	@Override
	public List<PackageType> getPackageTypes() throws ServiceException {
		String sql = "SELECT tbdak1, tbdad1, tbdad2, tbdad3 FROM fbfiles.scp002 WHERE TBDKEY = 'FDPKGC' ORDER BY tbdad3";
		List<PackageType> listQuery = null;
		try {
			listQuery =  jdbcCommonServices.query(sql, new PackageTypeMapper());	
			return listQuery;
		}
		catch(Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityDAOImpl.class, "getPackageTypes()", "** No package types found", e);
			throw new ServiceException("Package types could not be retrieved.");
		}	
	} // getPackageTypes
} 
