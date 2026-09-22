import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Split {
    public final int splitSize;
    public List<Map.Entry<CarClass, Integer>> grid = new LinkedList<>();

    public Split(int splitSize) {
        this.splitSize = splitSize;
    }

    public void orderClasses() {
        this.grid.sort(Map.Entry.comparingByKey(CarClass::compare));
    }

    public int classCount() {
        return grid.size();
    }

    public int driverCount() {
        int count = 0;
        for (Map.Entry<CarClass, Integer> entry : grid) {
            count += entry.getValue();
        }
        return count;
    }

    public int remainingGarages() {
        return splitSize - driverCount();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int classTarget = driverCount() / classCount();
        int classMinimum = classTarget / 2;
        sb.append(String.format("(%d out of %d expected drivers, class target = %d|%d)%n", driverCount(), splitSize, classTarget, classMinimum));
        for (Map.Entry<CarClass, Integer> carClass : grid) {
            sb.append(String.format("%9s - %d cars;%n", carClass.getKey(), carClass.getValue()));
        }
        return sb.toString();
    }
}
