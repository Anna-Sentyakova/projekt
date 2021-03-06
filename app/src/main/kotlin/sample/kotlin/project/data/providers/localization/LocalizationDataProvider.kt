package sample.kotlin.project.data.providers.localization

import sample.kotlin.project.domain.providers.localization.LocalizationProvider
import java.util.Locale
import javax.inject.Inject

class LocalizationDataProvider
@Inject constructor() : LocalizationProvider {

    companion object {
        private val SUPPORTED_LANGUAGE_CODES = arrayOf("en")
        private val DEFAULT_LANGUAGE_CODE = SUPPORTED_LANGUAGE_CODES[0]
    }

    override val systemLanguageCode: String get() = Locale.getDefault().language

    override val appLanguageCode get() = mapToSupportedLanguageCode(systemLanguageCode)

    private fun mapToSupportedLanguageCode(languageCode: String) =
        SUPPORTED_LANGUAGE_CODES.firstOrNull { it.equals(languageCode, ignoreCase = true) }
            ?: DEFAULT_LANGUAGE_CODE
}
