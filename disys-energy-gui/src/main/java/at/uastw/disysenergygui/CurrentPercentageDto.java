package at.uastw.disysenergygui;

public class CurrentPercentageDto {

    private double communityDepleted;
    private double gridPortion;


    public CurrentPercentageDto(){
    }

    public CurrentPercentageDto(double communityDepleted, double gridPortion){
        this.communityDepleted = communityDepleted;
        this.gridPortion = gridPortion;
    }

    public double getCommunityDepleted(){
        return communityDepleted;
    }

    public void setCommunityDepleted(double communityDepleted){
        this.communityDepleted = communityDepleted;
    }

    public double getGridPortion() {
        return gridPortion;
    }

    public void setGridPortion(double gridPortion) {
        this.gridPortion = gridPortion;
    }


}
