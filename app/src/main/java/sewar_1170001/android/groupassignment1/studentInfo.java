package sewar_1170001.android.groupassignment1;

// POJO Class
public class studentInfo {
    private String fName ;
    private String lName ;
    private String loc;
    private String idNum ;
    private String dob ;

    public studentInfo() {
    }

    public studentInfo(String fName, String lName, String loc, String idNum, String dob) {
        this.fName = fName;
        this.lName = lName;
        this.loc = loc;
        this.idNum = idNum;
        this.dob = dob;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "studentInfo{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", loc='" + loc + '\'' +
                ", idNum='" + idNum + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
