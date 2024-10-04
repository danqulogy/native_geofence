// Autogenerated from Pigeon (v22.4.2), do not edit directly.
// See also: https://pub.dev/packages/pigeon
@file:Suppress("UNCHECKED_CAST", "ArrayInDataClass")


import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private fun wrapResult(result: Any?): List<Any?> {
  return listOf(result)
}

private fun wrapError(exception: Throwable): List<Any?> {
  return if (exception is FlutterError) {
    listOf(
      exception.code,
      exception.message,
      exception.details
    )
  } else {
    listOf(
      exception.javaClass.simpleName,
      exception.toString(),
      "Cause: " + exception.cause + ", Stacktrace: " + Log.getStackTraceString(exception)
    )
  }
}

private fun createConnectionError(channelName: String): FlutterError {
  return FlutterError("channel-error",  "Unable to establish connection on channel: '$channelName'.", "")}

/**
 * Error class for passing custom error details to Flutter via a thrown PlatformException.
 * @property code The error code.
 * @property message The error message.
 * @property details The error details. Must be a datatype supported by the api codec.
 */
class FlutterError (
  val code: String,
  override val message: String? = null,
  val details: Any? = null
) : Throwable()

/**
 * Geofencing events.
 *
 * See the helpful illustration at:
 * https://developer.android.com/develop/sensors-and-location/location/geofencing
 */
enum class GeofenceEvent(val raw: Int) {
  ENTER(0),
  EXIT(1),
  /** Not supported on iOS. */
  DWELL(2);

  companion object {
    fun ofRaw(raw: Int): GeofenceEvent? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

/** Errors that can occur when interacting with the native geofence API. */
enum class NativeGeofenceErrorCode(val raw: Int) {
  UNKNOWN(0),
  /** A plugin internal error. Please report these as bugs on GitHub. */
  PLUGIN_INTERNAL(1),
  /** The arguments passed to the method are invalid. */
  INVALID_ARGUMENTS(2),
  /** An error occurred while communicating with the native platform. */
  CHANNEL_ERROR(3),
  /**
   * The required location permission was not granted.
   *
   * On Android we need: `ACCESS_FINE_LOCATION`
   * On iOS we need: `NSLocationWhenInUseUsageDescription`
   *
   * Please use an external permission manager such as "permission_handler" to
   * request the permission from the user.
   */
  MISSING_LOCATION_PERMISSION(4),
  /**
   * The required background location permission was not granted.
   *
   * On Android we need: `ACCESS_BACKGROUND_LOCATION` (for API level 29+)
   * On iOS we need: `NSLocationAlwaysAndWhenInUseUsageDescription`
   *
   * Please use an external permission manager such as "permission_handler" to
   * request the permission from the user.
   */
  MISSING_BACKGROUND_LOCATION_PERMISSION(5),
  /**
   * The geofence deletion failed because the geofence was not found.
   * This is safe to ignore.
   */
  GEOFENCE_NOT_FOUND(6),
  /**
   * The specified geofence callback was not found.
   * This can happen for old geofence callback functions that were
   * moved/renamed. Please re-create those geofences.
   */
  CALLBACK_NOT_FOUND(7),
  /**
   * The specified geofence callback function signature is invalid.
   * This can happen if the callback function signature has changed or due to
   * plugin contract changes.
   */
  CALLBACK_INVALID(8);

  companion object {
    fun ofRaw(raw: Int): NativeGeofenceErrorCode? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class LocationWire (
  val latitude: Double,
  val longitude: Double
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): LocationWire {
      val latitude = pigeonVar_list[0] as Double
      val longitude = pigeonVar_list[1] as Double
      return LocationWire(latitude, longitude)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      latitude,
      longitude,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class IosGeofenceSettingsWire (
  val initialTrigger: Boolean
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): IosGeofenceSettingsWire {
      val initialTrigger = pigeonVar_list[0] as Boolean
      return IosGeofenceSettingsWire(initialTrigger)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      initialTrigger,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class AndroidGeofenceSettingsWire (
  val initialTriggers: List<GeofenceEvent>,
  val expirationDurationMillis: Long? = null,
  val loiteringDelayMillis: Long,
  val notificationResponsivenessMillis: Long? = null
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): AndroidGeofenceSettingsWire {
      val initialTriggers = pigeonVar_list[0] as List<GeofenceEvent>
      val expirationDurationMillis = pigeonVar_list[1] as Long?
      val loiteringDelayMillis = pigeonVar_list[2] as Long
      val notificationResponsivenessMillis = pigeonVar_list[3] as Long?
      return AndroidGeofenceSettingsWire(initialTriggers, expirationDurationMillis, loiteringDelayMillis, notificationResponsivenessMillis)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      initialTriggers,
      expirationDurationMillis,
      loiteringDelayMillis,
      notificationResponsivenessMillis,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class GeofenceWire (
  val id: String,
  val location: LocationWire,
  val radiusMeters: Double,
  val triggers: List<GeofenceEvent>,
  val iosSettings: IosGeofenceSettingsWire,
  val androidSettings: AndroidGeofenceSettingsWire,
  val callbackHandle: Long
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): GeofenceWire {
      val id = pigeonVar_list[0] as String
      val location = pigeonVar_list[1] as LocationWire
      val radiusMeters = pigeonVar_list[2] as Double
      val triggers = pigeonVar_list[3] as List<GeofenceEvent>
      val iosSettings = pigeonVar_list[4] as IosGeofenceSettingsWire
      val androidSettings = pigeonVar_list[5] as AndroidGeofenceSettingsWire
      val callbackHandle = pigeonVar_list[6] as Long
      return GeofenceWire(id, location, radiusMeters, triggers, iosSettings, androidSettings, callbackHandle)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      id,
      location,
      radiusMeters,
      triggers,
      iosSettings,
      androidSettings,
      callbackHandle,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class ActiveGeofenceWire (
  val id: String,
  val location: LocationWire,
  val radiusMeters: Double,
  val triggers: List<GeofenceEvent>,
  val androidSettings: AndroidGeofenceSettingsWire? = null
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): ActiveGeofenceWire {
      val id = pigeonVar_list[0] as String
      val location = pigeonVar_list[1] as LocationWire
      val radiusMeters = pigeonVar_list[2] as Double
      val triggers = pigeonVar_list[3] as List<GeofenceEvent>
      val androidSettings = pigeonVar_list[4] as AndroidGeofenceSettingsWire?
      return ActiveGeofenceWire(id, location, radiusMeters, triggers, androidSettings)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      id,
      location,
      radiusMeters,
      triggers,
      androidSettings,
    )
  }
}

/** Generated class from Pigeon that represents data sent in messages. */
data class GeofenceCallbackParamsWire (
  val geofences: List<ActiveGeofenceWire>,
  val event: GeofenceEvent,
  val location: LocationWire? = null,
  val callbackHandle: Long
)
 {
  companion object {
    fun fromList(pigeonVar_list: List<Any?>): GeofenceCallbackParamsWire {
      val geofences = pigeonVar_list[0] as List<ActiveGeofenceWire>
      val event = pigeonVar_list[1] as GeofenceEvent
      val location = pigeonVar_list[2] as LocationWire?
      val callbackHandle = pigeonVar_list[3] as Long
      return GeofenceCallbackParamsWire(geofences, event, location, callbackHandle)
    }
  }
  fun toList(): List<Any?> {
    return listOf(
      geofences,
      event,
      location,
      callbackHandle,
    )
  }
}
private open class FlutterBindingsPigeonCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      129.toByte() -> {
        return (readValue(buffer) as Long?)?.let {
          GeofenceEvent.ofRaw(it.toInt())
        }
      }
      130.toByte() -> {
        return (readValue(buffer) as Long?)?.let {
          NativeGeofenceErrorCode.ofRaw(it.toInt())
        }
      }
      131.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          LocationWire.fromList(it)
        }
      }
      132.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          IosGeofenceSettingsWire.fromList(it)
        }
      }
      133.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          AndroidGeofenceSettingsWire.fromList(it)
        }
      }
      134.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          GeofenceWire.fromList(it)
        }
      }
      135.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          ActiveGeofenceWire.fromList(it)
        }
      }
      136.toByte() -> {
        return (readValue(buffer) as? List<Any?>)?.let {
          GeofenceCallbackParamsWire.fromList(it)
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is GeofenceEvent -> {
        stream.write(129)
        writeValue(stream, value.raw)
      }
      is NativeGeofenceErrorCode -> {
        stream.write(130)
        writeValue(stream, value.raw)
      }
      is LocationWire -> {
        stream.write(131)
        writeValue(stream, value.toList())
      }
      is IosGeofenceSettingsWire -> {
        stream.write(132)
        writeValue(stream, value.toList())
      }
      is AndroidGeofenceSettingsWire -> {
        stream.write(133)
        writeValue(stream, value.toList())
      }
      is GeofenceWire -> {
        stream.write(134)
        writeValue(stream, value.toList())
      }
      is ActiveGeofenceWire -> {
        stream.write(135)
        writeValue(stream, value.toList())
      }
      is GeofenceCallbackParamsWire -> {
        stream.write(136)
        writeValue(stream, value.toList())
      }
      else -> super.writeValue(stream, value)
    }
  }
}


/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface NativeGeofenceApi {
  fun initialize(callbackDispatcherHandle: Long)
  fun createGeofence(geofence: GeofenceWire, callback: (Result<Unit>) -> Unit)
  fun reCreateAfterReboot()
  fun getGeofenceIds(): List<String>
  fun getGeofences(): List<ActiveGeofenceWire>
  fun removeGeofenceById(id: String, callback: (Result<Unit>) -> Unit)
  fun removeAllGeofences(callback: (Result<Unit>) -> Unit)

  companion object {
    /** The codec used by NativeGeofenceApi. */
    val codec: MessageCodec<Any?> by lazy {
      FlutterBindingsPigeonCodec()
    }
    /** Sets up an instance of `NativeGeofenceApi` to handle messages through the `binaryMessenger`. */
    @JvmOverloads
    fun setUp(binaryMessenger: BinaryMessenger, api: NativeGeofenceApi?, messageChannelSuffix: String = "") {
      val separatedMessageChannelSuffix = if (messageChannelSuffix.isNotEmpty()) ".$messageChannelSuffix" else ""
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceApi.initialize$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val callbackDispatcherHandleArg = args[0] as Long
            val wrapped: List<Any?> = try {
              api.initialize(callbackDispatcherHandleArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceApi.createGeofence$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val geofenceArg = args[0] as GeofenceWire
            api.createGeofence(geofenceArg) { result: Result<Unit> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                reply.reply(wrapResult(null))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceApi.reCreateAfterReboot$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.reCreateAfterReboot()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceApi.getGeofenceIds$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              listOf(api.getGeofenceIds())
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceApi.getGeofences$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              listOf(api.getGeofences())
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceApi.removeGeofenceById$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val idArg = args[0] as String
            api.removeGeofenceById(idArg) { result: Result<Unit> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                reply.reply(wrapResult(null))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceApi.removeAllGeofences$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            api.removeAllGeofences{ result: Result<Unit> ->
              val error = result.exceptionOrNull()
              if (error != null) {
                reply.reply(wrapError(error))
              } else {
                reply.reply(wrapResult(null))
              }
            }
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}
/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface NativeGeofenceBackgroundApi {
  fun triggerApiInitialized()
  fun promoteToForeground()
  fun demoteToBackground()

  companion object {
    /** The codec used by NativeGeofenceBackgroundApi. */
    val codec: MessageCodec<Any?> by lazy {
      FlutterBindingsPigeonCodec()
    }
    /** Sets up an instance of `NativeGeofenceBackgroundApi` to handle messages through the `binaryMessenger`. */
    @JvmOverloads
    fun setUp(binaryMessenger: BinaryMessenger, api: NativeGeofenceBackgroundApi?, messageChannelSuffix: String = "") {
      val separatedMessageChannelSuffix = if (messageChannelSuffix.isNotEmpty()) ".$messageChannelSuffix" else ""
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceBackgroundApi.triggerApiInitialized$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.triggerApiInitialized()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceBackgroundApi.promoteToForeground$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.promoteToForeground()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.native_geofence.NativeGeofenceBackgroundApi.demoteToBackground$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.demoteToBackground()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}
/** Generated class from Pigeon that represents Flutter messages that can be called from Kotlin. */
class NativeGeofenceTriggerApi(private val binaryMessenger: BinaryMessenger, private val messageChannelSuffix: String = "") {
  companion object {
    /** The codec used by NativeGeofenceTriggerApi. */
    val codec: MessageCodec<Any?> by lazy {
      FlutterBindingsPigeonCodec()
    }
  }
  fun geofenceTriggered(paramsArg: GeofenceCallbackParamsWire, callback: (Result<Unit>) -> Unit)
{
    val separatedMessageChannelSuffix = if (messageChannelSuffix.isNotEmpty()) ".$messageChannelSuffix" else ""
    val channelName = "dev.flutter.pigeon.native_geofence.NativeGeofenceTriggerApi.geofenceTriggered$separatedMessageChannelSuffix"
    val channel = BasicMessageChannel<Any?>(binaryMessenger, channelName, codec)
    channel.send(listOf(paramsArg)) {
      if (it is List<*>) {
        if (it.size > 1) {
          callback(Result.failure(FlutterError(it[0] as String, it[1] as String, it[2] as String?)))
        } else {
          callback(Result.success(Unit))
        }
      } else {
        callback(Result.failure(createConnectionError(channelName)))
      } 
    }
  }
}
