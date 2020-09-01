package org.ant.wed;

class Invitee {
    final private String name;
    final private int id;
    final private boolean wifeSide;
    final private boolean hasPlusOne;
    final private Category category;
    final private String subCategory;

    public Invitee(String name, int id, boolean wifeSide, Category category, String subCategory, boolean hasPlusOne) {
        this.name = name;
        this.id = id; //@TODO: check if needed
        this.wifeSide = wifeSide;
        this.category = category;
        this.subCategory = subCategory;
        this.hasPlusOne = hasPlusOne; //@TODO: check how to handle
    }

    public String getName() {
        return name;
    }

    public int evalEdge(Invitee other){

        if (other == null) return -10; //input check

        if (this.subCategory.equals(other.subCategory)){
            return 500;
        } else if (this.category == other.category) {
            boolean isMatchingSided = this.wifeSide == other.wifeSide;
            switch (this.category) {
                case FAMILY: {
                    return isMatchingSided ? 30 : 15;
                }
                case WORK:
                case FRIENDS: {
                    return isMatchingSided ? 30 : 20;
                }
            }

        } else {
            if(this.category == Category.FAMILY){
                return  other.category == Category.FRIENDS ? -30 : -50;
            }
            else if(this.category == Category.FRIENDS){
                return other.category == Category.FAMILY ? -30 : -10;
            }
            else{
                return other.category == Category.FAMILY ? -40 : -10;
            }
        }

        return  -1; /* Unreachable line */
    }

}
