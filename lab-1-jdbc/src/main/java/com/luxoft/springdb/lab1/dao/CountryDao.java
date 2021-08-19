package com.luxoft.springdb.lab1.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.luxoft.springdb.lab1.model.Country;

public class CountryDao {

	private static final String UPDATE_COUNTRY_NAME_SQL_1 = "update country SET name='";
	private static final String UPDATE_COUNTRY_NAME_SQL_2 = " where code_name='";

	public static final String[][] COUNTRY_INIT_DATA = { { "Australia", "AU" },
			{ "Canada", "CA" }, { "France", "FR" }, { "Hong Kong", "HK" },
			{ "Iceland", "IC" }, { "Japan", "JP" }, { "Nepal", "NP" },
			{ "Russian Federation", "RU" }, { "Sweden", "SE" },
			{ "Switzerland", "CH" }, { "United Kingdom", "GB" },
			{ "United States", "US" } };

	private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

	private final NamedParameterJdbcTemplate namedJdbc;

	public CountryDao(NamedParameterJdbcTemplate namedJdbc) {
		this.namedJdbc = namedJdbc;
	}

	public List<Country> getCountryList() {
		return namedJdbc.query("select * from country",
				COUNTRY_ROW_MAPPER);
	}

	public List<Country> getCountryListStartWith(String name) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource(
				"name", name + "%");
		return namedJdbc.query("select * from country where name like :name",
				sqlParameterSource, COUNTRY_ROW_MAPPER);
	}

	public void updateCountryName(String codeName, String newCountryName) {
		// TODO: implement it
	}

	public void loadCountries() {
		Map<String, String> map = new HashMap<>();
		for (String[] countryData : COUNTRY_INIT_DATA) {
			map.clear();
			map.put("name", countryData[0]);
			map.put("code_name", countryData[1]);
			namedJdbc.update("insert into country (name, code_name) values (:name, :code_name)", map);
		}
	}

	public Country getCountryByCodeName(String codeName) {
		String sql = "select * from country where code_name = '" + codeName + "'";
		return namedJdbc.query(sql, COUNTRY_ROW_MAPPER).get(0);
	}

	public Country getCountryByName(String name)
			throws CountryNotFoundException {
		List<Country> countryList = namedJdbc.query("select * from country where name = '"
				+ name + "'", COUNTRY_ROW_MAPPER);
		if (countryList.isEmpty()) {
			throw new CountryNotFoundException();
		}
		return countryList.get(0);
	}
}
