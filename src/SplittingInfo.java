import java.util.List;
import java.util.Map;

public class SplittingInfo {
    public final int numberOfGarages;
    public final List<Map.Entry<CarClass, Integer>> signUpsPerClass;
    public final int signUpsTotal;

    public SplittingInfo(int gridSize, List<Map.Entry<CarClass, Integer>> signUpsPerClass, int signUpsTotal) {
        this.numberOfGarages = gridSize;
        this.signUpsPerClass = signUpsPerClass;
        this.signUpsTotal = signUpsTotal;

        sortClasses();
    }

    public int numberOfClasses() {
        return signUpsPerClass.size();
    }

    public void sortClasses() {
        signUpsPerClass.sort(Map.Entry.comparingByValue());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Splitting info (%d sign ups):%n", signUpsTotal));
        for (Map.Entry<CarClass, Integer> entry : signUpsPerClass)
            sb.append(String.format("Class: %s with %d sign ups;%n", entry.getKey(), entry.getValue()));
        return sb.toString();
    }
}
