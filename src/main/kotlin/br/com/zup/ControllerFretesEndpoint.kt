package br.com.zup

import com.google.protobuf.Any
import com.google.rpc.Code
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class ControllerFretesEndpoint : FretesGrpcServiceGrpc.FretesGrpcServiceImplBase() {
    override fun calculaFrete(request: CalculaFreteRequest?, responseObserver: StreamObserver<CalculaFreteResponse>?) {


        var cep = request?.cep

        if (cep.isNullOrBlank()) {
            responseObserver?.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("Cep deve ser informado")
                    .asRuntimeException()
            )
        }

        if (!cep?.matches("[0-9]{5}-[0-9]{3}".toRegex())!!) {

            responseObserver?.onError(
                Status.INVALID_ARGUMENT
                    .withDescription("Cep invalido")
                    .augmentDescription("formato esperado deve ser 99999-999")
                    .asRuntimeException()
            )
        }

        //erro de seguranca

        if (cep!!.endsWith("333")) {

            var statusProto = com.google.rpc.Status.newBuilder().setCode(Code.PERMISSION_DENIED.number)
                .setMessage("Usuario não pode acessar esse recurso")
                .addDetails(
                    Any.pack(
                    ErroDetails.newBuilder()
                        .setCode(403)
                        .setErro("Token inspirado").build()
                ))
                .build()

            responseObserver?.onError(io.grpc.protobuf.StatusProto.toStatusRuntimeException((statusProto)))
        }


        var valor = 0.0

        try {
            valor = Random.nextDouble(0.0, 190.0)

            throw IllegalArgumentException("Erro inesperado ao executar regrabde negocio")
        } catch (e: Exception) {

            responseObserver?.onError(
                Status.INTERNAL.withDescription(e.message)
                    .withCause(e.cause).asRuntimeException()
            )

        }


        val build =
            CalculaFreteResponse.newBuilder()
                .setCep(request?.cep)
                .setValor(valor)
                .build()

        responseObserver?.onNext(build)
        responseObserver?.onCompleted()

    }
}