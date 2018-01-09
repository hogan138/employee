package com.wanchang.employee.easemob.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by stormlei on 2017/5/23.
 */

public class BlockMsg extends RealmObject {

  @PrimaryKey
  private String blockId;
  private boolean isMsgBlock;

  public String getBlockId() {
    return blockId;
  }

  public void setBlockId(String blockId) {
    this.blockId = blockId;
  }

  public boolean isMsgBlock() {
    return isMsgBlock;
  }

  public void setMsgBlock(boolean msgBlock) {
    isMsgBlock = msgBlock;
  }
}
