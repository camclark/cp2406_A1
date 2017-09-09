public class LightCycle {
    public class Bike {
        // Directions are labeled clockwise
        public static final int DIRECTION_NORTH = 0;
        public static final int DIRECTION_EAST = 1;
        public static final int DIRECTION_SOUTH = 2;
        public static final int DIRECTION_WEST = 3;

        public int xPosition;
        public int yPosition;
        public int[][] gridArray;
        public int player;
        private int direction;

        public void Bike(int initialDirection, int _xPosition, int _yPosition, int[][] _gridArray, int _player) {
            direction = initialDirection;
            xPosition = _xPosition;
            yPosition = _yPosition;
            gridArray = _gridArray;
            player = _player;
            gridArray[xPosition][yPosition] = player;
        }

        // To turn in the opposite direction to which you were coming is an illegal move as the bike will crash
        public void turnWest() {
            if (direction != DIRECTION_EAST) {
                direction = DIRECTION_WEST;
            }
        }

        public void turnEast() {
            if (direction != DIRECTION_WEST) {
                direction = DIRECTION_EAST;
            }
        }

        public void turnSouth() {
            if (direction != DIRECTION_NORTH) {
                direction = DIRECTION_SOUTH;
            }
        }

        public void turnNorth() {
            if (direction != DIRECTION_SOUTH) {
                direction = DIRECTION_NORTH;
            }
        }
    }
}
