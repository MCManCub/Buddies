package me.Man_cub.Buddies.protocol.codec.entity;

import java.io.IOException;
import java.util.List;

import me.Man_cub.Buddies.protocol.ChannelBufferUtils;
import me.Man_cub.Buddies.protocol.message.entity.EntityMetadataMessage;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.spout.api.protocol.MessageCodec;
import org.spout.api.util.Parameter;

public final class EntityMetadataCodec extends MessageCodec<EntityMetadataMessage> {
	
	public EntityMetadataCodec() {
		super(EntityMetadataMessage.class, 0x28);
	}

	@Override
	public EntityMetadataMessage decode(ChannelBuffer buffer) throws IOException {
		int id = buffer.readInt();
		List<Parameter<?>> parameters = ChannelBufferUtils.readParameters(buffer);
		return new EntityMetadataMessage(id, parameters);
	}

	@Override
	public ChannelBuffer encode(EntityMetadataMessage message) throws IOException {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		buffer.writeInt(message.getEntityId());
		ChannelBufferUtils.writeParameters(buffer, message.getParameters());
		return buffer;
	}

}
