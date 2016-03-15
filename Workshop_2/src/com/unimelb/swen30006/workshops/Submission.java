package com.unimelb.swen30006.workshops;

class Submission {
	public int attemptNum;
	public int numFiles;
	private File[] files;
    // Return two fake files
    public File[] includedFiles(){
        File[] files = new File[2];
        files[0] = new File();
        files[1] = new File();
        return files;
    }
}
