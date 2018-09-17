package com.demo.quartz.service;

/**
 * ´¥·¢Æ÷×´Ì¬
 * @ClassName: TriggerState 
 * @Description: TODO
 */
public interface TriggerState {

    // STATES
    String STATE_WAITING = "WAITING"; 

    String STATE_ACQUIRED = "ACQUIRED"; 

    String STATE_EXECUTING = "EXECUTING";

    String STATE_COMPLETE = "COMPLETE";

    String STATE_BLOCKED = "BLOCKED";

    String STATE_ERROR = "ERROR";

    String STATE_PAUSED = "PAUSED";
}