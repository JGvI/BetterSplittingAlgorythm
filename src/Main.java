void main() {
  final SplittingInfo info = splittingInfo();


  int numberOfSplits = info.signUpsTotal / info.numberOfGarages + (info.signUpsTotal % info.numberOfGarages == 0 ? 0 : 1);
  if (numberOfSplits == 0) {
    System.out.println("No sign ups...");
    return;
  }
  int baseDriversPerSplit = info.signUpsTotal / numberOfSplits;
  int extraDrivers = info.signUpsTotal % numberOfSplits;

  // createSplits
  Split[] splits = new Split[numberOfSplits];
  for (int i = 0 ; i < splits.length; i++) {
    boolean addExtra = extraDrivers > 0;
    int driversThisSplit = baseDriversPerSplit;
    if (addExtra) {
      driversThisSplit++;
      extraDrivers--;
    }
    splits[i] = new Split(driversThisSplit);

    boolean lastSplit = i == splits.length - 1;

    // give drivers
    for (int j = 0; j < info.signUpsPerClass.size(); j++) {
      int classTarget = (splits[i].remainingGarages()) / (info.signUpsPerClass.size() - j);

      int currentDrivers = info.signUpsPerClass.get(j).getValue();

      int driversToAdd;
      if (lastSplit) {
        driversToAdd = currentDrivers;
      }
      else if (currentDrivers >= 2 * classTarget) {
        driversToAdd = classTarget;
      }
      else if (currentDrivers > classTarget) {
        driversToAdd = currentDrivers / 2 + (currentDrivers % 2 == 0 ? 0 : 1);
      }
      else {
        driversToAdd = currentDrivers;
      }

      splits[i].grid.add(Map.entry(info.signUpsPerClass.get(j).getKey(), driversToAdd));

      int remainingDriversInClass = currentDrivers - driversToAdd;
      if (remainingDriversInClass == 0) {
        info.signUpsPerClass.remove(j);
        j--;
      } else {
        info.signUpsPerClass.set(j, Map.entry(info.signUpsPerClass.get(j).getKey(), remainingDriversInClass));
      }
    }
    info.sortClasses();
    splits[i].orderClasses();
  }
  
  //print results
  System.out.printf("%n%s%n", info);
  StringBuilder sb = new StringBuilder();
  sb.append(String.format("Unallocated drivers:%n"));
  if (info.signUpsPerClass.isEmpty())
    sb.append(String.format("None :)%n"));
  else {
    for (Map.Entry<CarClass, Integer> classInfo : info.signUpsPerClass) {
      sb.append(String.format("%9s - %4d%n", classInfo.getKey(), classInfo.getValue()));
    }
  }
  sb.append(String.format("%nSplits:%n"));
  for (int i = 0; i < splits.length; i++) {
    sb.append(String.format("Split %2d ", i + 1));
    sb.append(splits[i].toString());
    sb.append(String.format("%n"));
  }
  System.out.println(sb);
}

private SplittingInfo splittingInfo() {
  CarClass[] classList = {CarClass.HY, CarClass.LMP2_ELMS, CarClass.LMP2_WEC, CarClass.LMP3, CarClass.GTE, CarClass.GT3};

  Scanner scanner = new Scanner(System.in);
  System.out.println("Enter maximum grid size:");
  int splitSize = scanner.nextInt();
  List<Map.Entry<CarClass, Integer>> signUps = new ArrayList<>();
  int totalSignUps = 0;
    for (CarClass carClass : classList) {
        System.out.printf("Enter number of sign ups for class %s:%n", carClass);
        int signUp = scanner.nextInt();
        if (signUp > 0) {
            signUps.add(Map.entry(carClass, signUp));
            totalSignUps += signUp;
        }
    }
  return new SplittingInfo(splitSize, signUps, totalSignUps);
}
