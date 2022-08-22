package me.devgabi.zipzop.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@Suppress("UNCHECKED_CAST")
suspend fun <PrivateKeyType, PublicKeyType> KeyFactory.getKeyPair(
  publicKeyPath: String,
  privateKeyPath: String
): Pair<PublicKeyType, PrivateKeyType> {
  val publicKey = withContext(Dispatchers.IO) {
    val bytes: ByteArray = Files.readAllBytes(Path.of(publicKeyPath))

    generatePublic(X509EncodedKeySpec(bytes)) as PublicKeyType
  }
  val privateKey = withContext(Dispatchers.IO) {
    val bytes: ByteArray = Files.readAllBytes(Path.of(privateKeyPath))

    generatePrivate(PKCS8EncodedKeySpec(bytes)) as PrivateKeyType
  }

  return publicKey to privateKey
}
