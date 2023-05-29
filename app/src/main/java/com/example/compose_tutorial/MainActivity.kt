package com.example.compose_tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_tutorial.ui.theme.Compose_TutorialTheme
//Importações 'Coluna e linha'
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
//Importações da Imagem
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
//Importações para estruturar o layout da mensagem
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
//Importações da borda e do 'MaterialTheme'
import androidx.compose.foundation.border
import androidx.compose.material3.Surface
//Importação do 'Tema Escuro'
import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
//Importações que exibem várias mensagens
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//Importações de animação das mensagens ao abri-las
import androidx.compose.foundation.clickable
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

//****CONCEITOS IMPORTANTES *******
//Material Design = foi criado com base em três pilares: Color (cor), Typography (tipografia) e Shape (forma)
//MaterialTheme.colors = defini o estilo das cores
//MaterialTheme.typography = define o estilo da tipografia
//MaterialTheme.shapes = permite personalizar a forma e a elevação do corpo da mensagem em uma 'Surface'
//Modifier = permitem alterar o tamanho, o layout e a aparência dos elementos ou tornar um elemento clicável


class MainActivity : ComponentActivity() {
    //onCreate = método
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContent = define o layout da atividade em que as funções de composição são chamadas
        setContent {
            //ComposeTutorialTheme = usamos os estilos do 'Material Design'
            Compose_TutorialTheme {
                //Chamando a função 'Conversation'
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

//Declarando as variáveis "author" e "body" como String
data class Message(val author: String, val body: String)
//Compasable = torna uma função composta
@Composable
//MessageCard = função que recebe um nome e o usa para configurar o elemento de texto.
fun MessageCard(msg: Message) {
    //Row = organiza os elementos horizontalmente
    //Modifier = permitem alterar o tamanho, o layout e a aparência dos elementos ou tornar um elemento clicável
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            //Adicionando uma foto do perfil do remetente
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                //Definir o tamanho da imagem para 40 dp
                .size(40.dp)
                //Imagem de clipe a ser moldada como um círculo
                .clip(CircleShape)
                //Definiindo o design da borda através do 'MaterialTheme.colors'
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        //Adicionando um espaço horizontal entre a imagem e a coluna
        Spacer(modifier = Modifier.width(8.dp))


        //Acompanhamos se a mensagem é expandida ou não pelo 'var'
        var isExpanded by remember { mutableStateOf(false) }


        //surfaceColor = será atualizado gradualmente de uma cor para a outra
        //animateColorAsState = função que é uma animação que muda a cor direto
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )


        //Column = permite organizar os elementos verticalmente
        //Alternamos a variável 'isExpanded' quando clicamos nesta Coluna
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            //Instanciando as variáveis declaradas na classe de dados "Message"
            Text(
                //Configurando a cor e o estilo do 'author'
                //Definiindo a cor através do 'MaterialTheme.colors'
                msg.author, color = MaterialTheme.colors.secondaryVariant
                        //Definindo o estilo através do 'MaterialTheme.typography'
                        style = MaterialTheme.typography.subtitle2
            )
            //Adicionando um espaço vertical entre o autor e os textos de mensagens
            Spacer(modifier = Modifier.height(4.dp))



            //Corpo da mensagem envolto em uma 'Surface' onde se personaliza a forma e a elevação do corpo através do 'MaterialTheme.shapes'
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                //surfaceColor = cor mudará gradualmente de primário para superfície
                color = surfaceColor,
                //animateContentSize = alterará o tamanho da superfície gradualmente
                modifier = Modifier.animateContentSize().padding(1.dp)

            ){
                Text(
                    //Configurando o estilo do 'body'
                    text = msg.body,
                    //Alterando o 'padding' através do 'Modifier'
                    modifier = Modifier.padding(all = 4.dp),
                    //Se a mensagem for expandida, exibiremos todo o seu conteúdo
                    //caso contrário, exibiremos apenas a primeira linha
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    //Definindo o estilo através do 'MaterialTheme.typography.body2'
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}


//Preview = permite visualizar as funções de composição no Android
@Preview(name = "Light Mode")
//Adicionando o 'Dark Mode'(Tema Escuro)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)


@Composable
//PreviewMessageCard = segunda função de composição que não usa parâmetros
fun PreviewMessageCard() {
//O PreviewMessageCard chama o "MessageCard"
    MessageCard(
        msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
    )
}


@Composable
//Conversation = função que lista as mensagens
fun Conversation(messages: List<Message>) {
    //LazyColumn = processa os elementos visíveis na tela
    LazyColumn {
        items(messages) { message ->
            //Chamada da função 'MessageCard'
            MessageCard(message)
        }
    }
}


@Preview
@Composable
//PreviewConversation = visualização da função 'Conversation'
fun PreviewConversation() {
    Compose_TutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}


//As mensagens
object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Colleague",
            "Test...Test...Test..."
        ),
        Message(
            "Colleague",
            "List of Android versions:\n" +
                    "Android KitKat (API 19)\n" +
                    "Android Lollipop (API 21)\n" +
                    "Android Marshmallow (API 23)\n" +
                    "Android Nougat (API 24)\n" +
                    "Android Oreo (API 26)\n" +
                    "Android Pie (API 28)\n" +
                    "Android 10 (API 29)\n" +
                    "Android 11 (API 30)\n" +
                    "Android 12 (API 31)\n"
        ),
        Message(
            "Colleague",
            "I think Kotlin is my favorite programming language.\n" +
                    "It's so much fun!"
        ),
        Message(
            "Colleague",
            "Searching for alternatives to XML layouts..."
        ),
        Message(
            "Colleague",
            "Hey, take a look at Jetpack Compose, it's great!\n" +
                    "It's the Android's modern toolkit for building native UI." +
                    "It simplifies and accelerates UI development on Android." +
                    "Less code, powerful tools, and intuitive Kotlin APIs :)"
        ),
        Message(
            "Colleague",
            "It's available from API 21+ :)"
        ),
        Message(
            "Colleague",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Colleague",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Colleague",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Colleague",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Colleague",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Colleague",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Colleague",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}