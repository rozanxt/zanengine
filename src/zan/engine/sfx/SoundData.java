package zan.engine.sfx;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class SoundData {

	private final int id;
	private final int format;
	private final int sampling;

	public SoundData(ShortBuffer data, int format, int sampling) {
		id = alGenBuffers();
		this.format = format;
		this.sampling = sampling;
		alBufferData(id, format, data, sampling);
	}

	public void delete() {
		alDeleteBuffers(id);
	}

	public int getID() {
		return id;
	}

	public int getFormat() {
		return format;
	}

	public int getSampling() {
		return sampling;
	}

	public static SoundData loadFromFile(String path) {
		SoundData sound = null;
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer c = stack.mallocInt(1);
			IntBuffer s = stack.mallocInt(1);
			ShortBuffer data = stb_vorbis_decode_filename(path, c, s);
			int channels = c.get();
			int sampling = s.get();
			int format = -1;
			if (channels == 1) {
				format = AL_FORMAT_MONO16;
			} else if (channels == 2) {
				format = AL_FORMAT_STEREO16;
			}
			sound = new SoundData(data, format, sampling);
			MemoryUtil.memFree(data);
		}
		return sound;
	}

}
