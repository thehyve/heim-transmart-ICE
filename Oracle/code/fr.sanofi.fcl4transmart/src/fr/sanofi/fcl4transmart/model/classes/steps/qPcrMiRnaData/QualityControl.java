/*******************************************************************************
 * Copyright (c) 2012 Sanofi-Aventis Recherche et Developpement.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *    Sanofi-Aventis Recherche et Developpement - initial API and implementation
 ******************************************************************************/
package fr.sanofi.fcl4transmart.model.classes.steps.qPcrMiRnaData;

import java.io.File;

import fr.sanofi.fcl4transmart.controllers.FileHandler;
import fr.sanofi.fcl4transmart.model.classes.dataType.QPcrMiRnaData;
import fr.sanofi.fcl4transmart.model.classes.workUI.qPcrMiRnaData.QualityControlUI;
import fr.sanofi.fcl4transmart.model.interfaces.DataTypeItf;
import fr.sanofi.fcl4transmart.model.interfaces.StepItf;
import fr.sanofi.fcl4transmart.model.interfaces.WorkItf;
/**
 *This class represents the quality control step for miRNA qPCR data
 */	
public class QualityControl implements StepItf{
	private WorkItf workUI;
	private DataTypeItf dataType;
	public QualityControl(DataTypeItf dataType){
		this.workUI=new QualityControlUI(dataType);
		this.dataType=dataType;
	}
	@Override
	public WorkItf getWorkUI() {
		return this.workUI;
	}
	public String toString(){
		return "Quality control";
	}
	public String getDescription(){
		return "This step allows controlling miRNA qPCR data loading quality.\n"+
				"The intensity values for transcript are displayed for all samples, coming from the raw data file and from the database.\n"+
				"A transcript has to be chosen. This transcript name has to be present in the raw miRNA qPCR data file.\n"+
				"Values for each sample are get in the raw files and in the database, and displayed. It is also indicated if theses values are the same in the two cases (with an allowed round of 0.001).\n"+
				"A database connection is needed for this step.";
	}
	public boolean isAvailable(){
		try{
			if(((QPcrMiRnaData)this.dataType).getRawFiles()==null || ((QPcrMiRnaData)this.dataType).getRawFiles().size()==0){
				return false;
			}
			File stsmf=((QPcrMiRnaData)this.dataType).getMappingFile();
			if(stsmf==null){
				return false;
			}
			if(!FileHandler.checkPlatform(stsmf)){
				return false;
			}
			if(!FileHandler.checkCategoryCodes(stsmf)){
				return false;
			}
			if(((QPcrMiRnaData)this.dataType).getLogFile()==null){
				return false;
			}
			return true;
		}
		catch(NullPointerException e){
			return false;
		}
	}
}
