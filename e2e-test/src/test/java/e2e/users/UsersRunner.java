package e2e.users;

import com.intuit.karate.junit5.Karate;

class UsersRunner {

  @Karate.Test
  Karate testInventories() {
    return Karate.run("users").relativeTo(getClass());
  }
}
