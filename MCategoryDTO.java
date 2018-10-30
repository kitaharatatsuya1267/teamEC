package com.internousdev.i1810a.dto;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class MCategoryDTO {
	private int id;
	private int categoryId;
	private String categoryName;
	private String categoryDescription;
	private Date insertDate;
	private Date updateDate;
}
