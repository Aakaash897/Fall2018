package test.col.cs.risk.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit class for executing all Controller test cases together in a row.
 * 
 * @author Team25
 */
@RunWith(Suite.class)
@SuiteClasses({ StartGameControllerTest.class, PlayerSettingsControllerTest.class, GameControllerTest.class })
public class ControllerTestSuit {

}
