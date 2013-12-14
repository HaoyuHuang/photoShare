package com.photoshare.bean;

import java.util.Map;

public class MainServiceTask extends TaskType
{

	private int _taskID;
	private Map<String, Object> _task;
	private String _activityName;
	
	public static final String ACTIVITY_NAME="ActivityName";
	
	public MainServiceTask(int taskID, Map<String, Object> task,String activityName)
	{
		this._task = task;
		this._taskID = taskID;
		this._activityName=activityName;
	}

	public String get_activityName()
	{
		return _activityName;
	}

	public void set_activityName(String _activityName)
	{
		this._activityName = _activityName;
	}

	public int get_taskID()
	{
		return _taskID;
	}

	public void set_taskID(int _taskID)
	{
		this._taskID = _taskID;
	}

	public Map<String, Object> get_task()
	{
		return _task;
	}

	public void set_task(Map<String, Object> _task)
	{
		this._task = _task;
	}

}
