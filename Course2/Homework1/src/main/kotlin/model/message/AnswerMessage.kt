package model.message

import model.dto.AnswerDto

data class AnswerMessage(val answers: MutableList<AnswerDto>)