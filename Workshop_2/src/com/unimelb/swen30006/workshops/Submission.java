package com.unimelb.swen30006.workshops;
/**
 * 
 * @author Ricardo Garcia Rosas 643952
 *
 */
class Submission {
	public int attemptNum;
	public int numFiles;
	private File[] files;
    // Return two fake files
    public File[] includedFiles(){
        File[] files = new File[2];
        files[0] = new File();
        files[1] = new File();
        numFiles = 2;
        return files;
    }
}
