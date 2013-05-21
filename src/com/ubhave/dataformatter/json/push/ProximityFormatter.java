/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

This library was developed as part of the EPSRC Ubhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.dataformatter.json.push;

import org.json.simple.JSONObject;

import android.content.Context;

import com.ubhave.dataformatter.json.PushSensorJSONFormatter;
import com.ubhave.sensormanager.config.SensorConfig;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pushsensor.ProximityData;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class ProximityFormatter extends PushSensorJSONFormatter
{
	private final static String DISTANCE = "distance";
	private final static String MAX_RANGE = "maxRange";

	public ProximityFormatter(final Context context)
	{
		super(context, SensorUtils.SENSOR_TYPE_PROXIMITY);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addSensorSpecificData(JSONObject json, SensorData data)
	{
		ProximityData proximityData = (ProximityData) data;
		json.put(DISTANCE, proximityData.getDistance());
		json.put(MAX_RANGE, proximityData.getMaxRange());
	}
	
	@Override
	public SensorData toSensorData(String jsonString)
	{
		JSONObject jsonData = super.parseData(jsonString);
		if (jsonData != null)
		{
			long recvTimestamp = super.parseTimeStamp(jsonData);
			SensorConfig sensorConfig = super.getGenericConfig(jsonData);
			ProximityData data = new ProximityData(recvTimestamp, sensorConfig);
			try
			{
				data.setDistance(((Double) jsonData.get(DISTANCE)).floatValue());
				data.setMaxRange(((Double) jsonData.get(MAX_RANGE)).floatValue());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return data;
		}
		else return null;
	}
}
