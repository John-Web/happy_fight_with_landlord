package com.software_pro.common.entity;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
import com.google.protobuf.GeneratedMessageV3.FieldAccessorTable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class ServerTransferData {
    private static final Descriptor internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_descriptor;
    private static final FieldAccessorTable internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_fieldAccessorTable;
    private static FileDescriptor descriptor;

    private ServerTransferData() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }

    public static FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u001eServerTransferDataProtoc.proto\u0012\u001forg.nico.ratel.landlords.entity\"D\n\u0018ServerTransferDataProtoc\u0012\f\n\u0004code\u0018\u0001 \u0001(\t\u0012\f\n\u0004data\u0018\u0002 \u0001(\t\u0012\f\n\u0004info\u0018\u0003 \u0001(\tB5\n\u001forg.nico.ratel.landlords.entityB\u0012ServerTransferDatab\u0006proto3"};
        InternalDescriptorAssigner assigner = new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                ServerTransferData.descriptor = root;
                return null;
            }
        };
        FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new FileDescriptor[0], assigner);
        internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_descriptor = (Descriptor)getDescriptor().getMessageTypes().get(0);
        internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_fieldAccessorTable = new FieldAccessorTable(internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_descriptor, new String[]{"Code", "Data", "Info"});
    }

    public static final class ServerTransferDataProtoc extends GeneratedMessageV3 implements ServerTransferData.ServerTransferDataProtocOrBuilder {
        private static final long serialVersionUID = 0L;
        public static final int CODE_FIELD_NUMBER = 1;
        private volatile Object code_;
        public static final int DATA_FIELD_NUMBER = 2;
        private volatile Object data_;
        public static final int INFO_FIELD_NUMBER = 3;
        private volatile Object info_;
        private byte memoizedIsInitialized;
        private static final ServerTransferData.ServerTransferDataProtoc DEFAULT_INSTANCE = new ServerTransferData.ServerTransferDataProtoc();
        private static final Parser<ServerTransferData.ServerTransferDataProtoc> PARSER = new AbstractParser<ServerTransferData.ServerTransferDataProtoc>() {
            public ServerTransferData.ServerTransferDataProtoc parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ServerTransferData.ServerTransferDataProtoc(input, extensionRegistry);
            }
        };

        private ServerTransferDataProtoc(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
        }

        private ServerTransferDataProtoc() {
            this.memoizedIsInitialized = -1;
            this.code_ = "";
            this.data_ = "";
            this.info_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ServerTransferDataProtoc(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            } else {
                com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();

                try {
                    boolean done = false;

                    while(!done) {
                        int tag = input.readTag();
                        String s;
                        switch(tag) {
                            case 0:
                                done = true;
                                break;
                            case 10:
                                s = input.readStringRequireUtf8();
                                this.code_ = s;
                                break;
                            case 18:
                                s = input.readStringRequireUtf8();
                                this.data_ = s;
                                break;
                            case 26:
                                s = input.readStringRequireUtf8();
                                this.info_ = s;
                                break;
                            default:
                                if (!this.parseUnknownFieldProto3(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                }
                        }
                    }
                } catch (InvalidProtocolBufferException var12) {
                    throw var12.setUnfinishedMessage(this);
                } catch (IOException var13) {
                    throw (new InvalidProtocolBufferException(var13)).setUnfinishedMessage(this);
                } finally {
                    this.unknownFields = unknownFields.build();
                    this.makeExtensionsImmutable();
                }

            }
        }

        public static final Descriptor getDescriptor() {
            return ServerTransferData.internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return ServerTransferData.internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_fieldAccessorTable.ensureFieldAccessorsInitialized(ServerTransferData.ServerTransferDataProtoc.class, ServerTransferData.ServerTransferDataProtoc.Builder.class);
        }

        public String getCode() {
            Object ref = this.code_;
            if (ref instanceof String) {
                return (String)ref;
            } else {
                ByteString bs = (ByteString)ref;
                String s = bs.toStringUtf8();
                this.code_ = s;
                return s;
            }
        }

        public ByteString getCodeBytes() {
            Object ref = this.code_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.code_ = b;
                return b;
            } else {
                return (ByteString)ref;
            }
        }

        public String getData() {
            Object ref = this.data_;
            if (ref instanceof String) {
                return (String)ref;
            } else {
                ByteString bs = (ByteString)ref;
                String s = bs.toStringUtf8();
                this.data_ = s;
                return s;
            }
        }

        public ByteString getDataBytes() {
            Object ref = this.data_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.data_ = b;
                return b;
            } else {
                return (ByteString)ref;
            }
        }

        public String getInfo() {
            Object ref = this.info_;
            if (ref instanceof String) {
                return (String)ref;
            } else {
                ByteString bs = (ByteString)ref;
                String s = bs.toStringUtf8();
                this.info_ = s;
                return s;
            }
        }

        public ByteString getInfoBytes() {
            Object ref = this.info_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)ref);
                this.info_ = b;
                return b;
            } else {
                return (ByteString)ref;
            }
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            } else if (isInitialized == 0) {
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if (!this.getCodeBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 1, this.code_);
            }

            if (!this.getDataBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 2, this.data_);
            }

            if (!this.getInfoBytes().isEmpty()) {
                GeneratedMessageV3.writeString(output, 3, this.info_);
            }

            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            } else {
                size = 0;
                if (!this.getCodeBytes().isEmpty()) {
                    size += GeneratedMessageV3.computeStringSize(1, this.code_);
                }

                if (!this.getDataBytes().isEmpty()) {
                    size += GeneratedMessageV3.computeStringSize(2, this.data_);
                }

                if (!this.getInfoBytes().isEmpty()) {
                    size += GeneratedMessageV3.computeStringSize(3, this.info_);
                }

                size += this.unknownFields.getSerializedSize();
                this.memoizedSize = size;
                return size;
            }
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (!(obj instanceof ServerTransferData.ServerTransferDataProtoc)) {
                return super.equals(obj);
            } else {
                ServerTransferData.ServerTransferDataProtoc other = (ServerTransferData.ServerTransferDataProtoc)obj;
                boolean result = true;
                result = result && this.getCode().equals(other.getCode());
                result = result && this.getData().equals(other.getData());
                result = result && this.getInfo().equals(other.getInfo());
                result = result && this.unknownFields.equals(other.unknownFields);
                return result;
            }
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            } else {
                int hash = 41;
                hash = 19 * hash + getDescriptor().hashCode();
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCode().hashCode();
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getData().hashCode();
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getInfo().hashCode();
                hash = 29 * hash + this.unknownFields.hashCode();
                this.memoizedHashCode = hash;
                return hash;
            }
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ServerTransferData.ServerTransferDataProtoc)PARSER.parseFrom(data);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerTransferData.ServerTransferDataProtoc)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServerTransferData.ServerTransferDataProtoc)PARSER.parseFrom(data);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerTransferData.ServerTransferDataProtoc)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServerTransferData.ServerTransferDataProtoc)PARSER.parseFrom(data);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerTransferData.ServerTransferDataProtoc)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(InputStream input) throws IOException {
            return (ServerTransferData.ServerTransferDataProtoc)GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerTransferData.ServerTransferDataProtoc)GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseDelimitedFrom(InputStream input) throws IOException {
            return (ServerTransferData.ServerTransferDataProtoc)GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerTransferData.ServerTransferDataProtoc)GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(CodedInputStream input) throws IOException {
            return (ServerTransferData.ServerTransferDataProtoc)GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static ServerTransferData.ServerTransferDataProtoc parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerTransferData.ServerTransferDataProtoc)GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public ServerTransferData.ServerTransferDataProtoc.Builder newBuilderForType() {
            return newBuilder();
        }

        public static ServerTransferData.ServerTransferDataProtoc.Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static ServerTransferData.ServerTransferDataProtoc.Builder newBuilder(ServerTransferData.ServerTransferDataProtoc prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        public ServerTransferData.ServerTransferDataProtoc.Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new ServerTransferData.ServerTransferDataProtoc.Builder() : (new ServerTransferData.ServerTransferDataProtoc.Builder()).mergeFrom(this);
        }

        protected ServerTransferData.ServerTransferDataProtoc.Builder newBuilderForType(BuilderParent parent) {
            ServerTransferData.ServerTransferDataProtoc.Builder builder = new ServerTransferData.ServerTransferDataProtoc.Builder(parent);
            return builder;
        }

        public static ServerTransferData.ServerTransferDataProtoc getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ServerTransferData.ServerTransferDataProtoc> parser() {
            return PARSER;
        }

        public Parser<ServerTransferData.ServerTransferDataProtoc> getParserForType() {
            return PARSER;
        }

        public ServerTransferData.ServerTransferDataProtoc getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<ServerTransferData.ServerTransferDataProtoc.Builder> implements ServerTransferData.ServerTransferDataProtocOrBuilder {
            private Object code_;
            private Object data_;
            private Object info_;

            public static final Descriptor getDescriptor() {
                return ServerTransferData.internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return ServerTransferData.internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_fieldAccessorTable.ensureFieldAccessorsInitialized(ServerTransferData.ServerTransferDataProtoc.class, ServerTransferData.ServerTransferDataProtoc.Builder.class);
            }

            private Builder() {
                this.code_ = "";
                this.data_ = "";
                this.info_ = "";
                this.maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.code_ = "";
                this.data_ = "";
                this.info_ = "";
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (ServerTransferData.ServerTransferDataProtoc.alwaysUseFieldBuilders) {
                }

            }

            public ServerTransferData.ServerTransferDataProtoc.Builder clear() {
                super.clear();
                this.code_ = "";
                this.data_ = "";
                this.info_ = "";
                return this;
            }

            public Descriptor getDescriptorForType() {
                return ServerTransferData.internal_static_org_nico_ratel_landlords_entity_ServerTransferDataProtoc_descriptor;
            }

            public ServerTransferData.ServerTransferDataProtoc getDefaultInstanceForType() {
                return ServerTransferData.ServerTransferDataProtoc.getDefaultInstance();
            }

            public ServerTransferData.ServerTransferDataProtoc build() {
                ServerTransferData.ServerTransferDataProtoc result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                } else {
                    return result;
                }
            }

            public ServerTransferData.ServerTransferDataProtoc buildPartial() {
                ServerTransferData.ServerTransferDataProtoc result = new ServerTransferData.ServerTransferDataProtoc(this);
                result.code_ = this.code_;
                result.data_ = this.data_;
                result.info_ = this.info_;
                this.onBuilt();
                return result;
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder clone() {
                return (ServerTransferData.ServerTransferDataProtoc.Builder)super.clone();
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder setField(FieldDescriptor field, Object value) {
                return (ServerTransferData.ServerTransferDataProtoc.Builder)super.setField(field, value);
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder clearField(FieldDescriptor field) {
                return (ServerTransferData.ServerTransferDataProtoc.Builder)super.clearField(field);
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder clearOneof(OneofDescriptor oneof) {
                return (ServerTransferData.ServerTransferDataProtoc.Builder)super.clearOneof(oneof);
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
                return (ServerTransferData.ServerTransferDataProtoc.Builder)super.setRepeatedField(field, index, value);
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder addRepeatedField(FieldDescriptor field, Object value) {
                return (ServerTransferData.ServerTransferDataProtoc.Builder)super.addRepeatedField(field, value);
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder mergeFrom(Message other) {
                if (other instanceof ServerTransferData.ServerTransferDataProtoc) {
                    return this.mergeFrom((ServerTransferData.ServerTransferDataProtoc)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder mergeFrom(ServerTransferData.ServerTransferDataProtoc other) {
                if (other == ServerTransferData.ServerTransferDataProtoc.getDefaultInstance()) {
                    return this;
                } else {
                    if (!other.getCode().equals(null)) {
                        this.code_ = other.code_;
                        this.onChanged();
                    }
                    if (!other.getData().equals(null)) {
                        this.data_ = other.data_;
                        this.onChanged();
                    }

                    if (!other.getInfo().equals(null)) {
                        this.info_ = other.info_;
                        this.onChanged();
                    }

                    this.mergeUnknownFields(other.unknownFields);
                    this.onChanged();
                    return this;
                }
            }

            public final boolean isInitialized() {
                return true;
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ServerTransferData.ServerTransferDataProtoc parsedMessage = null;

                try {
                    parsedMessage = (ServerTransferData.ServerTransferDataProtoc)ServerTransferData.ServerTransferDataProtoc.PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (InvalidProtocolBufferException var8) {
                    parsedMessage = (ServerTransferData.ServerTransferDataProtoc)var8.getUnfinishedMessage();
                    throw var8.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }

                }

                return this;
            }

            public String getCode() {
                Object ref = this.code_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    this.code_ = s;
                    return s;
                } else {
                    return (String)ref;
                }
            }

            public ByteString getCodeBytes() {
                Object ref = this.code_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.code_ = b;
                    return b;
                } else {
                    return (ByteString)ref;
                }
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder setCode(String value) {
                if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.code_ = value;
                    this.onChanged();
                    return this;
                }
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder clearCode() {
                this.code_ = ServerTransferData.ServerTransferDataProtoc.getDefaultInstance().getCode();
                this.onChanged();
                return this;
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder setCodeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                } else {
                    ServerTransferData.ServerTransferDataProtoc.checkByteStringIsUtf8(value);
                    this.code_ = value;
                    this.onChanged();
                    return this;
                }
            }

            public String getData() {
                Object ref = this.data_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    this.data_ = s;
                    return s;
                } else {
                    return (String)ref;
                }
            }

            public ByteString getDataBytes() {
                Object ref = this.data_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.data_ = b;
                    return b;
                } else {
                    return (ByteString)ref;
                }
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder setData(String value) {
                if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.data_ = value;
                    this.onChanged();
                    return this;
                }
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder clearData() {
                this.data_ = ServerTransferData.ServerTransferDataProtoc.getDefaultInstance().getData();
                this.onChanged();
                return this;
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder setDataBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                } else {
                    ServerTransferData.ServerTransferDataProtoc.checkByteStringIsUtf8(value);
                    this.data_ = value;
                    this.onChanged();
                    return this;
                }
            }

            public String getInfo() {
                Object ref = this.info_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    this.info_ = s;
                    return s;
                } else {
                    return (String)ref;
                }
            }

            public ByteString getInfoBytes() {
                Object ref = this.info_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)ref);
                    this.info_ = b;
                    return b;
                } else {
                    return (ByteString)ref;
                }
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder setInfo(String value) {
                if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.info_ = value;
                    this.onChanged();
                    return this;
                }
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder clearInfo() {
                this.info_ = ServerTransferData.ServerTransferDataProtoc.getDefaultInstance().getInfo();
                this.onChanged();
                return this;
            }

            public ServerTransferData.ServerTransferDataProtoc.Builder setInfoBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                } else {
                    ServerTransferData.ServerTransferDataProtoc.checkByteStringIsUtf8(value);
                    this.info_ = value;
                    this.onChanged();
                    return this;
                }
            }

            public final ServerTransferData.ServerTransferDataProtoc.Builder setUnknownFields(UnknownFieldSet unknownFields) {
                return (ServerTransferData.ServerTransferDataProtoc.Builder)super.setUnknownFieldsProto3(unknownFields);
            }

            public final ServerTransferData.ServerTransferDataProtoc.Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (ServerTransferData.ServerTransferDataProtoc.Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }

    public interface ServerTransferDataProtocOrBuilder extends MessageOrBuilder {
        String getCode();

        ByteString getCodeBytes();

        String getData();

        ByteString getDataBytes();

        String getInfo();

        ByteString getInfoBytes();
    }
}

