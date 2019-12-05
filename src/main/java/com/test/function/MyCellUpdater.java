package com.test.function;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.jxls.command.CellDataUpdater;
import org.jxls.common.CellData;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiCellData;

public class MyCellUpdater implements CellDataUpdater {

	@Override
	public void updateCellData(CellData cellData, CellRef targetCell, Context context) {
		PoiCellData poiCellData = (PoiCellData) cellData;
		poiCellData.getCellStyle().setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		cellData.setDefaultValue("-");
	}
}
