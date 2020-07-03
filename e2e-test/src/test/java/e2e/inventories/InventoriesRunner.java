package e2e.inventories;

import com.intuit.karate.junit5.Karate;

class InventoriesRunner {

  @Karate.Test
  Karate testInventories() {
    return Karate.run("inventories").relativeTo(getClass());
  }
}
