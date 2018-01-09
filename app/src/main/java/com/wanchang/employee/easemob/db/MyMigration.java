package com.wanchang.employee.easemob.db;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by stormlei on 2017/5/23.
 */

public class MyMigration implements RealmMigration {

  @Override
  public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

    // DynamicRealm exposes an editable schema
    RealmSchema schema = realm.getSchema();

    // Migrate to version 1: Add a new class
    // Example:
    // public Person extends RealmObject {
    //     private String name;
    //     private int age;
    //     // getters and setters left out for brevity
    // }
//    LogUtils.e(oldVersion);
//    if (oldVersion == 0) {
//      schema.create("Person")
//          .addField("name", String.class)
//          .addField("age", int.class);
//      oldVersion++;
//    }
  }

}
