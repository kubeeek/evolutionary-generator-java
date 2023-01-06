package agh;

public class SimulationConfigVariant {
    public enum AnimalBehavior {
        FULL_PREDISPOSITION,
        LITTLE_CRAZY;

        @Override
        public String toString() {
            return switch (this) {
                case LITTLE_CRAZY -> "Nieco szalenstwa";
                case FULL_PREDISPOSITION -> "Pelna predyspozycja";
            };
        }
    }

    public enum PlantGrowth {
        TOXIC_GRAVES,
        EQUATOR;

        @Override
        public String toString() {
            return switch (this) {
                case TOXIC_GRAVES -> "Toksyczne trupy";
                case EQUATOR -> "Zalesione rowniki";
            };
        }
    }

    public enum Mutation {
        RANDOM,
        CORRECTED;

        @Override
        public String toString() {
            return switch (this) {
                case RANDOM -> "Pelna losowosc";
                case CORRECTED -> "Lekka korekta";
            };
        }
    }

    public enum MapLoop {
        HELL_PORTAL,
        GLOBE;

        @Override
        public String toString() {
            return switch (this) {
                case HELL_PORTAL -> "Piekielny portal";
                case GLOBE -> "Kula ziemska";
            };
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof String)
            return this.toString().equals(obj);

        return super.equals(obj);
    }
}
